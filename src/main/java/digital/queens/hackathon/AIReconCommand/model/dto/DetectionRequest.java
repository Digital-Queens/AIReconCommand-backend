package digital.queens.hackathon.AIReconCommand.model.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
public class DetectionRequest {
    private String drone_id;
    private Long frame_no;
    private Instant timestamp;
    private Map<String, Object> telemetry;
    private List<Map<String, Object>> detections;
}
