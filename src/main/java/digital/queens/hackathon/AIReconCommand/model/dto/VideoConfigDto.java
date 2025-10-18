package digital.queens.hackathon.AIReconCommand.model.dto;

import lombok.Data;

@Data
public class VideoConfigDto {
    private String file;
    private String camera_id;
    private String city;
    private double lat;
    private double lon;
    private String trigger_time;
}
