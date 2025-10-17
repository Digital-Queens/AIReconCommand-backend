package digital.queens.hackathon.AIReconCommand.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DetectionRequest {
    private String drone_id;
    private String timestamp;
    private int frame_no;
    private Map<String, Object> telemetry;
    private List<Map<String, Object>> detections;
}
