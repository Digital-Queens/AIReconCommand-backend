package digital.queens.hackathon.AIReconCommand.api;

import digital.queens.hackathon.AIReconCommand.model.dto.*;
import digital.queens.hackathon.AIReconCommand.service.DetectionModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FrameWebSocketController {

    private final DetectionModelService detectionService;
    private final SimpMessagingTemplate wsTemplate;

    @MessageMapping("/frame")
    public void receiveFrame(FrameRequest frame) {
        System.out.println("Received frame from camera " + frame.getCameraId());

        Map<String, Object> result = detectionService.analyzeFrame(frame.getFrameBase64());
        System.out.println("Detection service returned: " + result);


        boolean danger = (boolean) result.get("dangerDetected");
        List<Map<String, Object>> detections = (List<Map<String, Object>>) result.get("detections");

            DetectionEvent event = DetectionEvent.builder()
                    .cameraId(frame.getCameraId())
                    .latitude(frame.getLatitude())
                    .longitude(frame.getLongitude())
                    .timestamp(Instant.now())
                    .dangerDetected(danger)
                    .frameBase64(frame.getFrameBase64())
                    .detections(detections)
                    .build();
            System.out.println("Sending DetectionEvent to /topic/detections");
            wsTemplate.convertAndSend("/topic/detections", event);
    }

    @MessageMapping("/ping")
    public void ping() {
        System.out.println("Ping received via /app/ping");
    }

    @Scheduled(fixedRate = 5000)
    public void broadcastDemoEvent() {
        wsTemplate.convertAndSend("/topic/detections", new DetectionEvent());}


    }
