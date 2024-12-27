package africa.semicolon.toDo.dtos.responses.taskResponses;

import africa.semicolon.toDo.data.models.enums.TaskCategory;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarkTaskAsOverdueResponse {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskCategory category;
}
