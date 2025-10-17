package digital.queens.hackathon.AIReconCommand.repository;

import digital.queens.hackathon.AIReconCommand.model.DetectionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DetectionRepository extends MongoRepository<DetectionRecord, String> {}
