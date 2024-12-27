package africa.semicolon.toDo.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Analytics {
    @Id
    private String id;
    private String userId;
    private int completedTaskCount;
    private int pendingTaskCount;
    private int totalTaskCount;
    private double completedTaskPercentage;
    private double pendingTaskPercentage;
    private double completedToPendingRatio;
    private LocalDateTime lastUpdatedAt;
}
