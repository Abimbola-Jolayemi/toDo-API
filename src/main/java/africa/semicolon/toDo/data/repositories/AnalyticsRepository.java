package africa.semicolon.toDo.data.repositories;

import africa.semicolon.toDo.data.models.Analytics;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AnalyticsRepository extends MongoRepository<Analytics, Long> {
    Optional<Analytics> findByUserId(String userId);
}
