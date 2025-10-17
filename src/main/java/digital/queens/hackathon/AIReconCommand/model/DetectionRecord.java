package digital.queens.hackathon.AIReconCommand.model;

import org.springframework.data.annotation.Id;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Document("detections")
public class DetectionRecord {
    @Id
    private String id;
    private String droneId;
    private Instant timestamp;
    private int frameNo;
    private Map<String, Object> telemetry;
    private List<Map<String, Object>> detections;
}
