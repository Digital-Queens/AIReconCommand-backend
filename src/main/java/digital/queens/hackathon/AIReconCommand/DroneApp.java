package digital.queens.hackathon.AIReconCommand;

import digital.queens.hackathon.AIReconCommand.service.VideoStreamerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DroneApp implements CommandLineRunner {

    private final VideoStreamerService streamerService;

    public DroneApp(VideoStreamerService streamerService) {
        this.streamerService = streamerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DroneApp.class, args);
    }

    @Override
    public void run(String... args) {
        streamerService.startStreaming();
    }
}
