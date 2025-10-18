package digital.queens.hackathon.AIReconCommand.api;

import digital.queens.hackathon.AIReconCommand.model.Detection;
import digital.queens.hackathon.AIReconCommand.model.dto.DetectionRequest;
import digital.queens.hackathon.AIReconCommand.service.DetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detections")
@RequiredArgsConstructor
public class DetectionController {

    private final DetectionService detectionService;

    @PostMapping
    public ResponseEntity<?> receiveDetections(@RequestBody DetectionRequest request) {
        detectionService.saveDetection(request);
        return ResponseEntity.ok("Detection saved successfully");
    }

    @GetMapping
    public ResponseEntity<List<Detection>> listAll() {
        return ResponseEntity.ok(detectionService.getAllDetections());
    }
}


