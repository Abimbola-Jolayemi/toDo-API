package africa.semicolon.toDo.dtos.requests.taskRequests;

import africa.semicolon.toDo.data.models.enums.TaskCategory;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchTaskRequest {
    private String title;
    private String description;
    private LocalDateTime deadline;
    private TaskStatus status;
    private TaskCategory category;
}
