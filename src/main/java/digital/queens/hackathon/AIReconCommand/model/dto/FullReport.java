package digital.queens.hackathon.AIReconCommand.model.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class FullReport {

    String description;
    LocalTime time;
}
