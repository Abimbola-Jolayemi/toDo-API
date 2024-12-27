package africa.semicolon.toDo.dtos.requests.taskRequests;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GetTodayTaskRequest {
    private LocalDateTime todayDate;
}
