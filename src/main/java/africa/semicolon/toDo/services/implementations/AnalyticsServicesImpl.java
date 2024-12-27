package africa.semicolon.toDo.services.implementations;

import africa.semicolon.toDo.data.models.Analytics;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import africa.semicolon.toDo.data.repositories.AnalyticsRepository;
import africa.semicolon.toDo.data.models.Task;
import africa.semicolon.toDo.dtos.requests.analyticsRequests.AnalyticsRequest;
import africa.semicolon.toDo.dtos.requests.taskRequests.CompletedTaskRequest;
import africa.semicolon.toDo.dtos.responses.analyticsResponses.AnalyticsResponse;
import africa.semicolon.toDo.dtos.responses.taskResponses.CompletedTaskResponse;
import africa.semicolon.toDo.services.interfaces.AnalyticsServices;
import africa.semicolon.toDo.services.interfaces.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsServicesImpl implements AnalyticsServices {

    @Autowired
    private TaskServices taskService;

    @Autowired
    private AnalyticsRepository analyticsRepository;





//    @Override
//    public AnalyticsResponse generateAnalytics(AnalyticsRequest analyticsRequest) {
//        String userId = analyticsRequest.getUserId();
//        List<Task> tasks = taskService.getTasksByUserId(userId);
//        int completedTaskCount = (int) tasks.stream().filter(task -> task.getStatus() == TaskStatus.COMPLETED).count();
//        int pendingTaskCount = (int) tasks.stream().filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS).count();
//        int totalTaskCount = tasks.size();
//        double completedTaskPercentage = totalTaskCount > 0 ? (completedTaskCount * 100.0) / totalTaskCount : 0;
//        double pendingTaskPercentage = totalTaskCount > 0 ? (pendingTaskCount * 100.0) / totalTaskCount : 0;
//        double completedToPendingRatio = pendingTaskCount > 0 ? (double) completedTaskCount / pendingTaskCount : 0;
//        AnalyticsResponse responseDTO = new AnalyticsResponse();
//        responseDTO.setUserId(userId);
//        responseDTO.setCompletedTaskCount(completedTaskCount);
//        responseDTO.setPendingTaskCount(pendingTaskCount);
//        responseDTO.setTotalTaskCount(totalTaskCount);
//        responseDTO.setCompletedTaskPercentage(completedTaskPercentage);
//        responseDTO.setPendingTaskPercentage(pendingTaskPercentage);
//        responseDTO.setCompletedToPendingRatio(completedToPendingRatio);
//        Analytics analytics = new Analytics();
//        analytics.setUserId(userId);
//        analytics.setCompletedTaskCount(completedTaskCount);
//        analytics.setPendingTaskCount(pendingTaskCount);
//        analytics.setTotalTaskCount(totalTaskCount);
//        analytics.setCompletedTaskPercentage(completedTaskPercentage);
//        analytics.setPendingTaskPercentage(pendingTaskPercentage);
//        analytics.setCompletedToPendingRatio(completedToPendingRatio);
//        analytics.setLastUpdatedAt(LocalDateTime.now());
//        analyticsRepository.save(analytics);
//
//        return responseDTO;
//    }
//
//    @Override
//    public AnalyticsResponse getAnalyticsByUserId(String userId) {
//        Analytics analytics = analyticsRepository.findByUserId(userId)
//                .orElseThrow(() -> new IllegalStateException("Analytics not found for userId: " + userId));
//        AnalyticsResponse responseDTO = new AnalyticsResponse();
//        responseDTO.setUserId(analytics.getUserId());
//        responseDTO.setCompletedTaskCount(analytics.getCompletedTaskCount());
//        responseDTO.setPendingTaskCount(analytics.getPendingTaskCount());
//        responseDTO.setTotalTaskCount(analytics.getTotalTaskCount());
//        responseDTO.setCompletedTaskPercentage(analytics.getCompletedTaskPercentage());
//        responseDTO.setPendingTaskPercentage(analytics.getPendingTaskPercentage());
//        responseDTO.setCompletedToPendingRatio(analytics.getCompletedToPendingRatio());
//
//        return responseDTO;
//    }



}
