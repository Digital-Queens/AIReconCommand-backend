package digital.queens.hackathon.AIReconCommand.api;

import digital.queens.hackathon.AIReconCommand.model.DetectionRecord;
import digital.queens.hackathon.AIReconCommand.model.DetectionRequest;
import digital.queens.hackathon.AIReconCommand.repository.DetectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/detections")
@RequiredArgsConstructor
public class DetectionController {

    private final DetectionRepository repository;

    @PostMapping
    public ResponseEntity<?> receiveDetections(@RequestBody DetectionRequest request) {
        DetectionRecord record = new DetectionRecord();
        record.setDroneId(request.getDrone_id());
        record.setTimestamp(Instant.parse(request.getTimestamp()));
        record.setFrameNo(request.getFrame_no());
        record.setTelemetry(request.getTelemetry());
        record.setDetections(request.getDetections());
        repository.save(record);
        return ResponseEntity.ok(Map.of("status", "received"));
    }

    @GetMapping
    public List<DetectionRecord> listAll() {
        return repository.findAll();
    }
}


