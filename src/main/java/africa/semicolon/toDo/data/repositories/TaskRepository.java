package africa.semicolon.toDo.data.repositories;

import africa.semicolon.toDo.data.models.Task;
import africa.semicolon.toDo.data.models.enums.TaskCategory;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByStatus(TaskStatus status);

    List<Task> findByDeadline(LocalDateTime deadline);

    List<Task> findByCategory(TaskCategory category);

    List<Task> findAllByUserId(String userId);
}

