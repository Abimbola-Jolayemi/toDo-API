package africa.semicolon.toDo.dtos.responses.taskResponses;

import africa.semicolon.toDo.data.models.enums.TaskCategory;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import lombok.Data;

@Data
public class CompletedTaskResponse {
    public String id;
    public String title;
    public String description;
    public TaskStatus status;
    public TaskCategory category;
}
