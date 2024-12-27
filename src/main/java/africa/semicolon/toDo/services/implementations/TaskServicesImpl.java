package africa.semicolon.toDo.services.implementations;

import africa.semicolon.toDo.data.models.Task;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import africa.semicolon.toDo.data.repositories.TaskRepository;
import africa.semicolon.toDo.dtos.requests.taskRequests.*;
import africa.semicolon.toDo.dtos.responses.taskResponses.*;
import africa.semicolon.toDo.exceptions.*;
import africa.semicolon.toDo.mappers.Mapper;
import africa.semicolon.toDo.services.interfaces.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServicesImpl implements TaskServices{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public CreateTaskResponse createTask(CreateTaskRequest request) {
        validateRequest(request);

        Task task = new Task();
        task.setUserId(request.getUserId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCategory(request.getCategory());
        task.setStatus(request.getStatus());
        task.setDeadline(request.getDeadline());
        task.setCreatedAt(LocalDateTime.now());
        task.setReminderDateTime(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);

        CreateTaskResponse response = new CreateTaskResponse();
        response.setTaskId(savedTask.getId());
        response.setUserId(savedTask.getUserId());
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setCategory(savedTask.getCategory());
        response.setStatus(savedTask.getStatus());
        response.setDeadline(savedTask.getDeadline());
        response.setCreatedAt(savedTask.getCreatedAt());
        response.setReminderDateTime(savedTask.getReminderDateTime());

        return response;
    }

    private void validateRequest(CreateTaskRequest request) {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new MissingFieldException("Title is required");
        }
        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new MissingFieldException("Description is required");
        }
        if (request.getCategory() == null) {
            throw new MissingFieldException("Category is required");
        }
        if (request.getStatus() == null) {
            throw new MissingFieldException("Status is required");
        }
        if (request.getDeadline() == null) {
            throw new MissingFieldException("Deadline is required");
        }
        if (request.getDeadline() != null && request.getDeadline().isBefore(LocalDateTime.now())) {
            throw new InvalidDeadlineException("Deadline cannot be in the past.");
        }
        if (request.getDeadline().isAfter(LocalDateTime.now().plusYears(10))) {
            throw new InvalidDeadlineException("Deadline cannot be more than 10 years into the future");
        }
    }



    public List<SearchTaskResponse> searchTasks(SearchTaskRequest searchRequest) {
        List<Task> tasks = taskRepository.findAll();

        List<SearchTaskResponse> results = tasks.stream()
                .filter(task ->
                        searchRequest.getTitle() == null || searchRequest.getTitle().isEmpty() ||
                                task.getTitle().toLowerCase().contains(searchRequest.getTitle().toLowerCase())
                )
                .filter(task ->
                        searchRequest.getDescription() == null || searchRequest.getDescription().isEmpty() ||
                                task.getDescription().toLowerCase().contains(searchRequest.getDescription().toLowerCase())
                )
                .filter(task ->
                        searchRequest.getDeadline() == null || task.getDeadline().equals(searchRequest.getDeadline())
                )
                .filter(task ->
                        searchRequest.getStatus() == null || task.getStatus().equals(searchRequest.getStatus())
                )
                .filter(task ->
                        searchRequest.getCategory() == null || task.getCategory().equals(searchRequest.getCategory())
                )
                .map(Mapper::map)
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            throw new TaskNotFoundException("No tasks match the search criteria");
        }
        return results;
    }



    @Override
    public UpdateTaskResponse updateTask(UpdateTaskRequest updateTaskRequest) {
        String taskId = updateTaskRequest.getTaskId();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (updateTaskRequest.getTitle() != null) {
            task.setTitle(updateTaskRequest.getTitle());
        }
        if (updateTaskRequest.getDescription() != null) {
            task.setDescription(updateTaskRequest.getDescription());
        }
        if (updateTaskRequest.getCategory() != null) {
            task.setCategory(updateTaskRequest.getCategory());
        }
        if (updateTaskRequest.getStatus() != null) {
            task.setStatus(updateTaskRequest.getStatus());
        }
        if (updateTaskRequest.getDeadline() != null) {
            task.setDeadline(updateTaskRequest.getDeadline());
        }

        taskRepository.save(task);

        UpdateTaskResponse updateTaskResponse = new UpdateTaskResponse();
        updateTaskResponse.setTaskId(task.getId());
        updateTaskResponse.setTitle(task.getTitle());
        updateTaskResponse.setDescription(task.getDescription());
        updateTaskResponse.setCategory(task.getCategory());
        updateTaskResponse.setStatus(task.getStatus());
        updateTaskResponse.setDeadline(task.getDeadline());

        return updateTaskResponse;
    }


    public DeleteTaskResponse deleteTask(DeleteTaskRequest request) {
        Task task = taskRepository.findById(request.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        taskRepository.delete(task);
        DeleteTaskResponse response = new DeleteTaskResponse();
        response.setMessage("Task deleted successfully");
        return response;
    }

    @Override
    public List<GetAllTasksResponse> getAllTasks(GetAllTasksRequest request) {
        List<Task> tasks = taskRepository.findAllByUserId(request.getUserId());
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        return Mapper.mapToGetAllTasksResponseList(tasks);
    }

    @Override
    public MarkTaskAsCompletedResponse markTaskAsCompleted(MarkTaskAsCompletedRequest request) {
        Task task = taskRepository.findById(request.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        if (TaskStatus.COMPLETED.equals(task.getStatus())) {
            throw new IllegalStateException("Task is already completed");
        }
        task.setStatus(TaskStatus.COMPLETED);
        task = taskRepository.save(task);

        return new MarkTaskAsCompletedResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCategory()
        );
    }

    @Override
    public MarkTaskAsInProgressResponse markTaskAsInProgress(MarkTaskAsInProgressRequest request) {
        Task task = taskRepository.findById(request.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        if (TaskStatus.IN_PROGRESS.equals(task.getStatus())) {
            throw new IllegalStateException("Task is already in progress");
        }
        task.setStatus(TaskStatus.IN_PROGRESS);
        task = taskRepository.save(task);

        return new MarkTaskAsInProgressResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCategory()
        );
    }

    @Override
    public MarkTaskAsOverdueResponse markTaskAsOverdue(MarkTaskAsOverdueRequest request) {
        Task task = taskRepository.findById(request.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        if (TaskStatus.OVERDUE.equals(task.getStatus())) {
            throw new IllegalStateException("Task is already overdue");
        }
        task.setStatus(TaskStatus.OVERDUE);
        task = taskRepository.save(task);

        return new MarkTaskAsOverdueResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCategory()
        );
    }

    @Override
    public List<Task> getTasksByUserId(String userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    public DeleteAllTasksResponse deleteAllTasks(DeleteAllTasksRequest request) {
        List<Task> userTasks = taskRepository.findAllByUserId(request.getUserId());
        if (userTasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found for the user with ID: " + request.getUserId());
        }

        taskRepository.deleteAll(userTasks);
        DeleteAllTasksResponse response = new DeleteAllTasksResponse();
        response.setMessage("Deleted all tasks successfully");
        return response;
    }


    @Override
    public List<CompletedTaskResponse> getCompletedTask(CompletedTaskRequest completedTaskRequest) {
        GetAllTasksRequest getAllTasksRequest = new GetAllTasksRequest();
        getAllTasksRequest.setUserId(completedTaskRequest.getUserId());

        List<GetAllTasksResponse> allTasks = getAllTasks(getAllTasksRequest);

        return allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                .map(task -> mapToCompletedTaskResponse(task))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompletedTaskResponse> getInProgressTask(CompletedTaskRequest completedTaskRequest) {
        GetAllTasksRequest getAllTasksRequest = new GetAllTasksRequest();
        getAllTasksRequest.setUserId(completedTaskRequest.getUserId());

        List<GetAllTasksResponse> allTasks = getAllTasks(getAllTasksRequest);

        return allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS)
                .map(task -> mapToCompletedTaskResponse(task))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompletedTaskResponse> getPendingTask(CompletedTaskRequest completedTaskRequest) {
        GetAllTasksRequest getAllTasksRequest = new GetAllTasksRequest();
        getAllTasksRequest.setUserId(completedTaskRequest.getUserId());

        List<GetAllTasksResponse> allTasks = getAllTasks(getAllTasksRequest);

        return allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.PENDING)
                .map(task -> mapToCompletedTaskResponse(task))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompletedTaskResponse> getOverdueTask(CompletedTaskRequest completedTaskRequest) {
        GetAllTasksRequest getAllTasksRequest = new GetAllTasksRequest();
        getAllTasksRequest.setUserId(completedTaskRequest.getUserId());

        List<GetAllTasksResponse> allTasks = getAllTasks(getAllTasksRequest);

        return allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.OVERDUE)
                .map(task -> mapToCompletedTaskResponse(task))
                .collect(Collectors.toList());
    }

    private CompletedTaskResponse mapToCompletedTaskResponse(GetAllTasksResponse task) {
        CompletedTaskResponse response = new CompletedTaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setCategory(task.getCategory());
        return response;
    }


}

