package digital.queens.hackathon.AIReconCommand.service;

import digital.queens.hackathon.AIReconCommand.model.VideoRecord;
import digital.queens.hackathon.AIReconCommand.model.dto.DetectionEvent;
import digital.queens.hackathon.AIReconCommand.repository.VideoRecordRepository;
import lombok.RequiredArgsConstructor;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class VideoStreamerService {

    private final VideoRecordRepository repository;
    private final SimpMessagingTemplate wsTemplate;

    // Limit to 3 concurrent streams to protect GPU/CPU
    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    static {
        try {
            // Disable GPU hardware decoding → prevents Intel GPU resets
            System.setProperty("OPENCV_VIDEOIO_MSMF_ENABLE_HW_TRANSFORMS", "0");

            // Load OpenCV native lib
            nu.pattern.OpenCV.loadLocally();
            System.out.println("OpenCV loaded successfully");
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
            executor.submit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(delay);
                    streamVideo(video);
                } catch (Exception e) {
                    System.err.println("Error streaming " + video.getFileName() + ": " + e.getMessage());
                }
            });
        }
    }

    private void streamVideo(VideoRecord video) {
        System.out.println("🎥 Streaming video " + video.getFileName());

        VideoCapture cap = new VideoCapture(video.getFileName());
        if (!cap.isOpened()) {
            System.err.println("Cannot open video: " + video.getFileName());
            return;
        }

        double fps = cap.get(Videoio.CAP_PROP_FPS);
        long frameDelay = fps > 0 ? (long) (1000 / fps) : 33; // default 30fps
        org.opencv.core.Mat frame = new org.opencv.core.Mat();

        int frameNo = 0;
        while (cap.read(frame)) {
            frameNo++;

            // Process only every 5th frame (throttling)
            if (frameNo % 5 != 0) continue;

            String b64 = encodeFrame(frame);
            if (b64 == null) continue;

            // Simulate model output
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
                Thread.sleep(frameDelay * 5); // match throttling
            } catch (InterruptedException ignored) {}
        }

        cap.release();
        frame.release();
        System.out.println("Finished " + video.getFileName());
    }

    private String encodeFrame(org.opencv.core.Mat frame) {
        try {
            BufferedImage img = matToBufferedImage(frame);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
