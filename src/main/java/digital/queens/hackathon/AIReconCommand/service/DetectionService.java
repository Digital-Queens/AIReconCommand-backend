package digital.queens.hackathon.AIReconCommand.service;

import digital.queens.hackathon.AIReconCommand.model.Detection;
import digital.queens.hackathon.AIReconCommand.model.dto.DetectionRequest;
import digital.queens.hackathon.AIReconCommand.repository.DetectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DetectionService {

    private final DetectionRepository repository;

    public void saveDetection(DetectionRequest request) {
        List<Map<String, Object>> detections = request.getDetections();
        if (detections == null || detections.isEmpty()) {
            return;
        }

        List<Detection> docs = new ArrayList<>();

        for (Map<String, Object> det : detections) {
            Detection doc = Detection.builder()
                    .droneId(request.getDrone_id())
                    .frameNo(request.getFrame_no())
                    .timestamp(request.getTimestamp() != null ? request.getTimestamp() : Instant.now())
                    .data(det)
                    .build();
            docs.add(doc);
        }

        repository.saveAll(docs);
    }

    public List<Detection> getAllDetections() {
        return repository.findAll();
    }
}
