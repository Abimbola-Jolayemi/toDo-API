package africa.semicolon.toDo.dtos.requests.taskRequests;

import lombok.Data;

@Data
public class MarkTaskAsOverdueRequest {
    private String id;
}