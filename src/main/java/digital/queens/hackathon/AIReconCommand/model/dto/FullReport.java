package digital.queens.hackathon.AIReconCommand.model.dto;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
public class FullReport {

    Date date;
    String description;
    LocalTime time;
}
