package digital.queens.hackathon.AIReconCommand.api;


import digital.queens.hackathon.AIReconCommand.model.VideoRecord;
import digital.queens.hackathon.AIReconCommand.model.dto.VideoConfigDto;
import digital.queens.hackathon.AIReconCommand.service.VideoRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoRecordController {

    private final VideoRecordService service;

    @PostMapping("/load")
    public ResponseEntity<String> loadVideos(@RequestBody List<VideoConfigDto> videoList) {
        service.saveVideoRecords(videoList);
        return ResponseEntity.ok("Loaded " + videoList.size() + " videos into database.");
    }

    @GetMapping
    public List<VideoRecord> listAll() {
        return service.getAll();
    }
}