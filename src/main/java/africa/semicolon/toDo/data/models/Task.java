package africa.semicolon.toDo.data.models;

import africa.semicolon.toDo.data.models.enums.TaskCategory;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private TaskCategory category = null;
    private TaskStatus status = null;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime deadline;
    private String userId;
    private LocalDateTime reminderDateTime;
}
