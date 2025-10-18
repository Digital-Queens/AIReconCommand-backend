package digital.queens.hackathon.AIReconCommand.service;


import digital.queens.hackathon.AIReconCommand.model.VideoRecord;
import digital.queens.hackathon.AIReconCommand.model.dto.VideoConfigDto;
import digital.queens.hackathon.AIReconCommand.repository.VideoRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoRecordService {

    private final VideoRecordRepository repository;

    public void saveVideoRecords(List<VideoConfigDto> videos) {
        repository.deleteAll(); // clear old records

        Instant now = Instant.now();
        int index = 0;

        for (VideoConfigDto dto : videos) {
            VideoRecord record = VideoRecord.builder()
                    .cameraId(dto.getCamera_id())
                    .fileName(dto.getFile())
                    .city(dto.getCity())
                    .latitude(dto.getLat())
                    .longitude(dto.getLon())
                    .triggerTime(now.plus(index * 20L, ChronoUnit.SECONDS))
                    .build();
            repository.save(record);
            index++;
        }
    }

    public List<VideoRecord> getAll() {
        return repository.findAll();
    }
}
