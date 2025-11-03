package digital.queens.hackathon.AIReconCommand.model.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class FullReport {

    Date date;
    String description;
    LocalTime time;
}
