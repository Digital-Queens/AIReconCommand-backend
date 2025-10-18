package digital.queens.hackathon.AIReconCommand.repository;


import digital.queens.hackathon.AIReconCommand.model.VideoRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRecordRepository extends JpaRepository<VideoRecord, Long> {
}
