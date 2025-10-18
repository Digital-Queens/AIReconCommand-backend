package digital.queens.hackathon.AIReconCommand.repository;


import digital.queens.hackathon.AIReconCommand.model.Detection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectionRepository extends JpaRepository<Detection, Long> {}

