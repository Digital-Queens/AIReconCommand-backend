package digital.queens.hackathon.AIReconCommand.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DetectionModelService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> analyzeFrame(String base64) {
        try {
            Map<String, Object> body = Map.of("frameBase64", base64);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://localhost:5001/analyze-frame", request, String.class);

            JsonNode json = mapper.readTree(response.getBody());
            return mapper.convertValue(json, Map.class);
        } catch (Exception e) {
            System.err.println("Error contacting Python service: " + e.getMessage());
            return Map.of("dangerDetected", false, "detections", List.of());
        }
    }
}
