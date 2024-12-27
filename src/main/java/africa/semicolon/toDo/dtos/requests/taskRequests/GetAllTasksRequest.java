package africa.semicolon.toDo.dtos.requests.taskRequests;

import lombok.Data;

@Data
public class GetAllTasksRequest {
    private String userId;
}
