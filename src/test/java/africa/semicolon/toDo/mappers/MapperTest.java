package africa.semicolon.toDo.mappers;

import africa.semicolon.toDo.data.models.Task;
import africa.semicolon.toDo.data.models.enums.TaskCategory;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import africa.semicolon.toDo.dtos.responses.taskResponses.GetAllTasksResponse;
import africa.semicolon.toDo.dtos.responses.taskResponses.SearchTaskResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MapperTest {

    @Test
    public void testThatTaskCanMapToSearchTaskResponse() {
        Task task = new Task();
        task.setId("1");
        task.setTitle("Sample Task");
        task.setDescription("This is a sample task");
        task.setDeadline(LocalDateTime.of(2024, 12, 31, 23, 59));
        task.setStatus(TaskStatus.PENDING);
        task.setCategory(TaskCategory.WORK);

        SearchTaskResponse response = Mapper.map(task);

        assertEquals("1", response.getId());
        assertEquals("Sample Task", response.getTitle());
        assertEquals("This is a sample task", response.getDescription());
        assertEquals(LocalDateTime.of(2024, 12, 31, 23, 59), response.getDeadline());
        assertEquals(TaskStatus.PENDING, response.getStatus());
        assertEquals(TaskCategory.WORK, response.getCategory());
    }

    @Test
    public void testMapToGetAllTasksResponse(){
        Task task = new Task();
        task.setId("1");
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setStatus(TaskStatus.PENDING);
        task.setCategory(TaskCategory.WORK);
        GetAllTasksResponse response = Mapper.mapToGetAllTasksResponse(task);
        assertEquals("1", response.getId());
        assertEquals("Task Title", response.getTitle());
        assertEquals("Task Description", response.getDescription());
        assertEquals(TaskStatus.PENDING, response.getStatus());
        assertEquals(TaskCategory.WORK, response.getCategory());
    }

    @Test
    public void testMapToGetAllTasksResponseList() {
        Task task1 = new Task();
        task1.setId("1");
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setStatus(TaskStatus.PENDING);
        task1.setCategory(TaskCategory.WORK);
        Task task2 = new Task();
        task2.setId("2");
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setCategory(TaskCategory.PERSONAL);
        List<Task> tasks = List.of(task1, task2);
        List<GetAllTasksResponse> responses = Mapper.mapToGetAllTasksResponseList(tasks);
        assertEquals(2, responses.size());
        assertEquals("1", responses.get(0).getId());
        assertEquals("Task 1", responses.get(0).getTitle());
        assertEquals("Description 1", responses.get(0).getDescription());
        assertEquals(TaskStatus.PENDING, responses.get(0).getStatus());
        assertEquals(TaskCategory.WORK, responses.get(0).getCategory());
        assertEquals("2", responses.get(1).getId());
        assertEquals("Task 2", responses.get(1).getTitle());
        assertEquals("Description 2", responses.get(1).getDescription());
        assertEquals(TaskStatus.COMPLETED, responses.get(1).getStatus());
        assertEquals(TaskCategory.PERSONAL, responses.get(1).getCategory());
    }


}