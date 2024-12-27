package africa.semicolon.toDo.dtos.requests.taskRequests;

import africa.semicolon.toDo.data.models.enums.TaskCategory;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskRequest {
    private String taskId;
    private String title;
    private String description;
    private TaskCategory category;
    private TaskStatus status;
    private LocalDateTime deadline;
}
