package digital.queens.hackathon.AIReconCommand.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "drone_locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    private Long id;

    private String name;

    private String city;

    private double latitude;

    private double longitude;

}
