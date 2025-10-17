package digital.queens.hackathon.AIReconCommand.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@AllArgsConstructor
public class Point {
    private DangerCategory dangerCategory;
    private long latitude;
    private long longitude;
    private String cameraId;
    private String cameraName;
    private String videoUrl;

}
