package digital.queens.hackathon.AIReconCommand.model.dto;

import lombok.Data;

@Data
public class FrameRequest {
    private String frameBase64; // Base64 of image
    private String cameraId;    // Optional, to know source
    private double latitude;
    private double longitude;
}
