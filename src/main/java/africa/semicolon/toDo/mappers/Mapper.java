package africa.semicolon.toDo.mappers;

import africa.semicolon.toDo.data.models.Task;
import africa.semicolon.toDo.dtos.responses.taskResponses.GetAllTasksResponse;
import africa.semicolon.toDo.dtos.responses.taskResponses.SearchTaskResponse;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static SearchTaskResponse map(Task task) {
        SearchTaskResponse response = new SearchTaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setDeadline(task.getDeadline());
        response.setStatus(task.getStatus());
        response.setCategory(task.getCategory());
        return response;
    }

    public static List<SearchTaskResponse> map(List<Task> tasks) {
        return tasks.stream()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    public static GetAllTasksResponse mapToGetAllTasksResponse(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        return new GetAllTasksResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCategory()
        );
    }

    public static List<GetAllTasksResponse> mapToGetAllTasksResponseList(List<Task> tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("Task list cannot be null");
        }
        return tasks.stream()
                .map(Mapper::mapToGetAllTasksResponse)
                .collect(Collectors.toList());
    }


}
