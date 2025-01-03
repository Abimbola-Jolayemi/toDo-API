package africa.semicolon.toDo.services.interfaces;

import africa.semicolon.toDo.data.models.Task;
import africa.semicolon.toDo.dtos.requests.taskRequests.*;
import africa.semicolon.toDo.dtos.responses.taskResponses.*;

import java.util.List;

public interface TaskServices {
    CreateTaskResponse createTask(CreateTaskRequest request);
    List<SearchTaskResponse> searchTasks(SearchTaskRequest request);
    UpdateTaskResponse updateTask(UpdateTaskRequest request);
//    DeleteTaskResponse deleteTask(DeleteTaskRequest request);
    String deleteTask(String id);
    List<GetAllTasksResponse> getAllTasks(String userId);
    MarkTaskAsCompletedResponse markTaskAsCompleted(MarkTaskAsCompletedRequest request);
    MarkTaskAsInProgressResponse markTaskAsInProgress(MarkTaskAsInProgressRequest request);
    MarkTaskAsOverdueResponse markTaskAsOverdue(MarkTaskAsOverdueRequest request);
    List<Task> getTasksByUserId(String userId);
    DeleteAllTasksResponse deleteAllTasks(DeleteAllTasksRequest request);

    List<CompletedTaskResponse> getCompletedTask(CompletedTaskRequest completedTaskRequest);
    List<CompletedTaskResponse> getInProgressTask(CompletedTaskRequest completedTaskRequest);
    List<CompletedTaskResponse> getPendingTask(CompletedTaskRequest completedTaskRequest);
    List<CompletedTaskResponse> getOverdueTask(CompletedTaskRequest completedTaskRequest);

    String clearDb();
}
