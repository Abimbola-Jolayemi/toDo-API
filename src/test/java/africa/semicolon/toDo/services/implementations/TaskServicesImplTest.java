package africa.semicolon.toDo.services.implementations;

import africa.semicolon.toDo.data.models.Task;
import africa.semicolon.toDo.data.models.enums.TaskCategory;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import africa.semicolon.toDo.data.repositories.TaskRepository;
import africa.semicolon.toDo.dtos.requests.taskRequests.*;
import africa.semicolon.toDo.dtos.responses.taskResponses.*;
import africa.semicolon.toDo.exceptions.InvalidDeadlineException;
import africa.semicolon.toDo.exceptions.MissingFieldException;
import africa.semicolon.toDo.exceptions.TaskNotFoundException;
import africa.semicolon.toDo.services.interfaces.TaskServices;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServicesImplTest {

    @Autowired
    private TaskServices taskService;

    @Autowired
    private TaskRepository taskRepository;

    @AfterEach
    public void tearDown() {
        taskRepository.deleteAll();
    }

    @Test
    public void testThatTaskCanBeCreated() {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("Task 1");
        createTaskRequest.setDescription("Description 1");
        createTaskRequest.setCategory(TaskCategory.WORK);
        createTaskRequest.setStatus(TaskStatus.PENDING);
        createTaskRequest.setDeadline(LocalDateTime.now().plusDays(1));

        CreateTaskResponse createTaskResponse = taskService.createTask(createTaskRequest);

        assertEquals(createTaskRequest.getTitle(), createTaskResponse.getTitle());
        assertEquals(createTaskRequest.getDescription(), createTaskResponse.getDescription());
        assertEquals(createTaskRequest.getCategory(), createTaskResponse.getCategory());
        assertEquals(createTaskRequest.getStatus(), createTaskResponse.getStatus());
        assertEquals(createTaskRequest.getDeadline(), createTaskResponse.getDeadline());

        Task savedTask = taskRepository.findById(createTaskResponse.getTaskId()).orElseThrow(() -> new RuntimeException("Task not found"));
        assertEquals(createTaskRequest.getTitle(), savedTask.getTitle());
        assertEquals(createTaskRequest.getDescription(), savedTask.getDescription());
        assertEquals(createTaskRequest.getCategory(), savedTask.getCategory());
        assertEquals(createTaskRequest.getStatus(), savedTask.getStatus());
    }

    @Test
    public void testThatTaskCannotBeCreatedWithTheDeadlineInThePast() {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("Task 1");
        createTaskRequest.setDescription("Description 1");
        createTaskRequest.setCategory(TaskCategory.WORK);
        createTaskRequest.setStatus(TaskStatus.PENDING);
        createTaskRequest.setDeadline(LocalDateTime.now().minusDays(1));
        assertThrows(InvalidDeadlineException.class, () -> { taskService.createTask(createTaskRequest);
        });
    }

    @Test
    public void testThatTaskCannotBeCreatedWithMissingTitle() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setDescription("Description 1");
        request.setCategory(TaskCategory.WORK);
        request.setStatus(TaskStatus.PENDING);
        request.setDeadline(null);

        MissingFieldException exception = assertThrows(
                MissingFieldException.class,
                () -> taskService.createTask(request)
        );
        assertEquals("Title is required", exception.getMessage());
    }

    @Test
    public void testThatTaskCannotBeCreatedWithMissingDescription() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Task 1");
        request.setCategory(TaskCategory.WORK);
        request.setStatus(TaskStatus.PENDING);
        request.setDeadline(null);
        MissingFieldException exception = assertThrows(
                MissingFieldException.class,
                () -> taskService.createTask(request)
        );
        assertEquals("Description is required", exception.getMessage());
    }

    @Test
    public void testThatDeadlineTooFarIntoFutureThrowsException() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Test Task");
        request.setDescription("Test Description");
        request.setCategory(TaskCategory.WORK);
        request.setStatus(TaskStatus.PENDING);
        request.setDeadline(LocalDateTime.now().plusYears(11));

        InvalidDeadlineException exception = assertThrows(
                InvalidDeadlineException.class,
                () -> taskService.createTask(request)
        );
        assertEquals("Deadline cannot be more than 10 years into the future", exception.getMessage());
    }

    @Test
    public void testThatMultipleTasksCanBeSearchedByTitle() {
        CreateTaskRequest createTaskRequest1 = new CreateTaskRequest();
        createTaskRequest1.setTitle("Task 1");
        createTaskRequest1.setDescription("Description 1");
        createTaskRequest1.setCategory(TaskCategory.WORK);
        createTaskRequest1.setStatus(TaskStatus.PENDING);
        createTaskRequest1.setDeadline(LocalDateTime.now().plusDays(1));
        taskService.createTask(createTaskRequest1);

        CreateTaskRequest createTaskRequest2 = new CreateTaskRequest();
        createTaskRequest2.setTitle("Task 1");
        createTaskRequest2.setDescription("Description 2");
        createTaskRequest2.setCategory(TaskCategory.WORK);
        createTaskRequest2.setStatus(TaskStatus.PENDING);
        createTaskRequest2.setDeadline(LocalDateTime.now().plusDays(2));
        taskService.createTask(createTaskRequest2);

        SearchTaskRequest searchRequest = new SearchTaskRequest();
        searchRequest.setTitle("Task 1");
        List<SearchTaskResponse> foundTasks = taskService.searchTasks(searchRequest);

        assertEquals(2, foundTasks.size());
        assertEquals("Description 1", foundTasks.get(0).getDescription());
        assertEquals("Description 2", foundTasks.get(1).getDescription());
    }

    @Test
    public void testThatMultipleTasksCanBeSearchedByDescription() {
        CreateTaskRequest createTaskRequest1 = new CreateTaskRequest();
        createTaskRequest1.setTitle("Task 1");
        createTaskRequest1.setDescription("Description 1");
        createTaskRequest1.setCategory(TaskCategory.WORK);
        createTaskRequest1.setStatus(TaskStatus.PENDING);
        createTaskRequest1.setDeadline(LocalDateTime.now().plusDays(1));
        taskService.createTask(createTaskRequest1);

        CreateTaskRequest createTaskRequest2 = new CreateTaskRequest();
        createTaskRequest2.setTitle("Task 2");
        createTaskRequest2.setDescription("Description 1");
        createTaskRequest2.setCategory(TaskCategory.WORK);
        createTaskRequest2.setStatus(TaskStatus.PENDING);
        createTaskRequest2.setDeadline(LocalDateTime.now().plusDays(2));
        taskService.createTask(createTaskRequest2);

        SearchTaskRequest searchRequest = new SearchTaskRequest();
        searchRequest.setDescription("Description 1");
        List<SearchTaskResponse> foundTasks = taskService.searchTasks(searchRequest);
        assertEquals(2, foundTasks.size());
        assertEquals("Task 1", foundTasks.get(0).getTitle());
        assertEquals("Task 2", foundTasks.get(1).getTitle());
    }

    @Test
    public void testThatTaskCanBeUpdated() {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("Initial Task");
        createTaskRequest.setDescription("Initial Description");
        createTaskRequest.setCategory(TaskCategory.WORK);
        createTaskRequest.setStatus(TaskStatus.PENDING);
        createTaskRequest.setDeadline(LocalDateTime.now().plusDays(1));
        CreateTaskResponse createdTaskResponse = taskService.createTask(createTaskRequest);

        String taskId = createdTaskResponse.getTaskId();
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setTaskId(createdTaskResponse.getTaskId());
        updateTaskRequest.setTitle("Updated Task");
        updateTaskRequest.setDescription("Updated Description");
        updateTaskRequest.setCategory(TaskCategory.WORK);
        updateTaskRequest.setStatus(TaskStatus.COMPLETED);
        updateTaskRequest.setDeadline(LocalDateTime.now().plusDays(2));

        UpdateTaskResponse updateTaskResponse = taskService.updateTask(updateTaskRequest);

        assertEquals(taskId, updateTaskResponse.getTaskId());
        assertEquals("Updated Task", updateTaskResponse.getTitle());
        assertEquals("Updated Description", updateTaskResponse.getDescription());
        assertEquals(TaskCategory.WORK, updateTaskResponse.getCategory());
        assertEquals(TaskStatus.COMPLETED, updateTaskResponse.getStatus());
        assertEquals(LocalDateTime.now().plusDays(2).toLocalDate(), updateTaskResponse.getDeadline().toLocalDate());

        Task updatedTask = taskRepository.findById(taskId).orElseThrow();
        assertEquals("Updated Task", updatedTask.getTitle());
        assertEquals("Updated Description", updatedTask.getDescription());
        assertEquals(TaskCategory.WORK, updatedTask.getCategory());
        assertEquals(TaskStatus.COMPLETED, updatedTask.getStatus());
        assertEquals(LocalDateTime.now().plusDays(2).toLocalDate(), updatedTask.getDeadline().toLocalDate());
    }

    @Test
    public void testUpdateNonExistingTaskThrowsException() {
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setTaskId("non-existing-task");
        updateTaskRequest.setTitle("Updated Task Title");
        updateTaskRequest.setDescription("Updated Task Description");
        TaskNotFoundException thrown = assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(updateTaskRequest);
        });
        assertEquals("Task not found", thrown.getMessage());
    }

    @Test
    public void testThatTaskCanBeDeletedSuccessfully() {
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("Task 1");
        createTaskRequest.setDescription("Description 1");
        createTaskRequest.setCategory(TaskCategory.WORK);
        createTaskRequest.setStatus(TaskStatus.PENDING);
        createTaskRequest.setDeadline(LocalDateTime.now().plusDays(1));
        CreateTaskResponse createdTaskResponse = taskService.createTask(createTaskRequest);
        String taskId = createdTaskResponse.getTaskId();
        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
        deleteTaskRequest.setId(taskId);
        DeleteTaskResponse deleteTaskResponse = taskService.deleteTask(deleteTaskRequest);
        assertEquals("Task deleted successfully", deleteTaskResponse.getMessage());
        Optional<Task> deletedTask = taskRepository.findById(taskId);
        assertFalse(deletedTask.isPresent());
    }

    @Test
    public void testDeleteNonExistingTaskThrowsException() {
        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
        deleteTaskRequest.setId("non-existing-task-id");
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.deleteTask(deleteTaskRequest);
        });
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void testMarkTaskAsCompleted() {

        Task task = new Task();
        task.setId("123");
        task.setTitle("Test Task");
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(task);

        assertTrue(TaskStatus.IN_PROGRESS.equals(task.getStatus()));
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());

        MarkTaskAsCompletedRequest taskDTO = new MarkTaskAsCompletedRequest();
        taskDTO.setId("123");

        MarkTaskAsCompletedResponse completedTask = taskService.markTaskAsCompleted(taskDTO);

        Task updatedTask = taskRepository.findById("123").orElseThrow(() -> new RuntimeException("Task not found"));
        assertTrue(TaskStatus.COMPLETED.equals(updatedTask.getStatus()));
        assertEquals(TaskStatus.COMPLETED, updatedTask.getStatus());
    }


    @Test
    void testMarkCompletedTaskAsCompleted() {
        Task task = new Task();
        task.setId("123");
        task.setTitle("Test Task");
        task.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task);
        MarkTaskAsCompletedRequest taskDTO = new MarkTaskAsCompletedRequest();
        taskDTO.setId("123");
        assertThrows(IllegalStateException.class, () -> taskService.markTaskAsCompleted(taskDTO));
    }

}
