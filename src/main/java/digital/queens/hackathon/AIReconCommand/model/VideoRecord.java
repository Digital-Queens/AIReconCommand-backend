package digital.queens.hackathon.AIReconCommand.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "video_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cameraId;

    private String fileName;

    private String city;

    private double latitude;

    private double longitude;

    private Instant triggerTime;
}