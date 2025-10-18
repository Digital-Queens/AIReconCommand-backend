package digital.queens.hackathon.AIReconCommand.model.dto;

import lombok.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetectionEvent {
    private String cameraId;
    private String city;
    private Instant timestamp;
    private double latitude;
    private double longitude;
    private boolean dangerDetected;
    private List<Map<String, Object>> detections;
    private String frameBase64;
}
