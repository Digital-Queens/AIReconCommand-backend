package digital.queens.hackathon.AIReconCommand.service;




import digital.queens.hackathon.AIReconCommand.model.VideoRecord;
import digital.queens.hackathon.AIReconCommand.model.dto.DetectionEvent;
import digital.queens.hackathon.AIReconCommand.repository.VideoRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

@Service
@RequiredArgsConstructor
public class VideoStreamerService {

    private final VideoRecordRepository repository;
    private final SimpMessagingTemplate wsTemplate;

    static {
        try {
            nu.pattern.OpenCV.loadLocally();  // loads native DLL automatically
        } catch (Exception e) {
            System.err.println("Failed to load OpenCV: " + e.getMessage());
        }
    }

    @Async
    public void startStreaming() {
        List<VideoRecord> videos = repository.findAll();
        videos.sort(Comparator.comparing(VideoRecord::getTriggerTime));

        for (VideoRecord video : videos) {
            long delay = Math.max(0,
                    video.getTriggerTime().toEpochMilli() - Instant.now().toEpochMilli());
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException ignored) {}

            new Thread(() -> streamVideo(video)).start();
        }
    }

    private void streamVideo(VideoRecord video) {
        System.out.println("Streaming video " + video.getFileName());

        VideoCapture cap = new VideoCapture(video.getFileName());
        if (!cap.isOpened()) {
            System.err.println("Cannot open video: " + video.getFileName());
            return;
        }

        double fps = cap.get(Videoio.CAP_PROP_FPS);
        long frameDelay = (long) (1000 / fps);
        org.opencv.core.Mat frame = new org.opencv.core.Mat();

        int frameNo = 0;
        while (cap.read(frame)) {
            frameNo++;
            String b64 = encodeFrame(frame);

            // here you could call AI model and replace detections accordingly
            DetectionEvent event = DetectionEvent.builder()
                    .cameraId(video.getCameraId())
                    .city(video.getCity())
                    .timestamp(Instant.now())
                    .latitude(video.getLatitude())
                    .longitude(video.getLongitude())
                    .dangerDetected(false)
                    .detections(List.of())
                    .frameBase64(b64)
                    .build();

            wsTemplate.convertAndSend("/topic/detections", event);

            try {
                Thread.sleep(frameDelay);
            } catch (InterruptedException ignored) {}
        }

        cap.release();
    }

    private String encodeFrame(org.opencv.core.Mat frame) {
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            BufferedImage img = matToBufferedImage(frame);
            ImageIO.write(img, "jpg", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            return null;
        }
    }

    private BufferedImage matToBufferedImage(org.opencv.core.Mat mat) {
        int type = BufferedImage.TYPE_3BYTE_BGR;
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] b = new byte[bufferSize];
        mat.get(0, 0, b);
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        byte[] targetPixels = ((java.awt.image.DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
}
