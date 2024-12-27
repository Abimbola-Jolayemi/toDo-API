package africa.semicolon.toDo.data.repositories;

import africa.semicolon.toDo.data.models.Task;
import africa.semicolon.toDo.data.models.enums.TaskCategory;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setId("1");
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setCreatedAt(LocalDateTime.now());
        task.setDeadline(LocalDateTime.now());
        task.setUserId("1");
        task.setCategory(TaskCategory.WORK);
        task.setStatus(TaskStatus.PENDING);
        task.setReminderDateTime(LocalDateTime.now());
    }

    @AfterEach
    public void tearDown() {
        taskRepository.deleteAll();
    }

    @Test
    public void testThatRepositoryIsEmpty_NoTaskIsAdded() {
        assertEquals(0, taskRepository.count());
    }

    @Test
    public void testThatRepositoryIsEmpty_TaskIsAdded() {
        taskRepository.save(task);
        assertEquals(1, taskRepository.count());
    }

    @Test
    public void testThatRepositoryCanFindTaskByStatus() {
        taskRepository.save(task);
        List<Task> tasks = taskRepository.findByStatus(TaskStatus.PENDING);
        assertEquals(1, tasks.size());
        assertEquals(task.getTitle(), tasks.get(0).getTitle());
        assertEquals(task.getDescription(), tasks.get(0).getDescription());
    }

    @Test
    public void testThatRepositoryCanFindTaskByCategory() {
        taskRepository.save(task);
        List<Task> tasks = taskRepository.findByCategory(TaskCategory.WORK);
        assertEquals(1, tasks.size());
        assertEquals(task.getTitle(), tasks.get(0).getTitle());
        assertEquals(task.getDescription(), tasks.get(0).getDescription());
    }

//    @Test
//    public void testThatRepositoryCanFindTaskByDueDate() {
//        taskRepository.save(task);
//        List<Task> tasks = taskRepository.findByDueDate(task.getDueDate());
//        assertEquals(1, tasks.size());
//        assertEquals(task.getTitle(), tasks.get(0).getTitle());
//        assertEquals(task.getDescription(), tasks.get(0).getDescription());
//        assertEquals(task.getDueDate(), tasks.get(0).getDueDate());
//    }

    @Test
    public void testThatRepositoryCanFindMultipleTasksByDueDate() {
        Task anotherTask = new Task();
        anotherTask.setId("2");
        anotherTask.setTitle("Another Task Title");
        anotherTask.setDescription("Another Task Description");
        anotherTask.setCreatedAt(LocalDateTime.now());
        anotherTask.setDeadline(task.getDeadline());
        anotherTask.setUserId("2");
        anotherTask.setCategory(TaskCategory.PERSONAL);
        anotherTask.setStatus(TaskStatus.COMPLETED);
        anotherTask.setReminderDateTime(LocalDateTime.now());
        taskRepository.save(task);
        taskRepository.save(anotherTask);
        List<Task> tasks = taskRepository.findByDeadline(task.getDeadline());
        assertEquals(2, tasks.size());
    }

    @Test
    public void testThatRepositoryReturnsEmptyListForNonMatchingDueDate() {
        taskRepository.save(task);
        List<Task> tasks = taskRepository.findByDeadline(task.getDeadline().plusDays(1));
        assertEquals(0, tasks.size());
    }

}
