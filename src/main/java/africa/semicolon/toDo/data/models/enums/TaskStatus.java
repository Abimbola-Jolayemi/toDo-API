package africa.semicolon.toDo.data.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    OVERDUE;
}
