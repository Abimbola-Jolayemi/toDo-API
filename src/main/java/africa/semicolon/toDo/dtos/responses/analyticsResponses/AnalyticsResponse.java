package africa.semicolon.toDo.dtos.responses.analyticsResponses;

import lombok.Data;

@Data
public class AnalyticsResponse {
    private String userId;
    private int completedTaskCount;
    private int pendingTaskCount;
    private int totalTaskCount;
    private double completedTaskPercentage;
    private double pendingTaskPercentage;
    private double completedToPendingRatio;
}
