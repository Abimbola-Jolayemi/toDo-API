//package africa.semicolon.toDo.services.implementations;
//
//import africa.semicolon.toDo.data.models.enums.TaskCategory;
//import africa.semicolon.toDo.data.models.enums.TaskStatus;
//import africa.semicolon.toDo.data.repositories.AnalyticsRepository;
//import africa.semicolon.toDo.data.repositories.TaskRepository;
//import africa.semicolon.toDo.dtos.requests.analyticsRequests.AnalyticsRequest;
//import africa.semicolon.toDo.dtos.requests.taskRequests.CreateTaskRequest;
//import africa.semicolon.toDo.dtos.responses.analyticsResponses.AnalyticsResponse;
//import africa.semicolon.toDo.services.interfaces.AnalyticsServices;
//import africa.semicolon.toDo.services.interfaces.TaskServices;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test") // Use test profile
//public class AnalyticsServicesImplTest {
//
//    @Autowired
//    private AnalyticsServices analyticsService;
//
//    @Autowired
//    private TaskServices taskService;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private AnalyticsRepository analyticsRepository;
//
//    @BeforeEach
//    void setUp() {
//        analyticsRepository.deleteAll();
//        taskRepository.deleteAll();
//    }
//
//    @Test
//    void testGenerateAnalytics_SingleUser() {
//        // Arrange
//        createTaskForUser("user123", "Complete the project", "This task involves completing the project.", TaskCategory.WORK, TaskStatus.COMPLETED);
//        createTaskForUser("user123", "Start the new module", "This task involves starting a new module.", TaskCategory.WORK, TaskStatus.IN_PROGRESS);
//
//        AnalyticsRequest request = new AnalyticsRequest();
//        request.setUserId("user123");
//
//        // Act
//        AnalyticsResponse response = analyticsService.generateAnalytics(request);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals("user123", response.getUserId());
//        assertEquals(1, response.getCompletedTaskCount());
//        assertEquals(1, response.getPendingTaskCount());
//        assertEquals(2, response.getTotalTaskCount());
//        assertEquals(50.0, response.getCompletedTaskPercentage());
//        assertEquals(50.0, response.getPendingTaskPercentage());
//        assertEquals(1.0, response.getCompletedToPendingRatio());
//    }
//
//    @Test
//    void testGenerateAnalytics_NoTasks() {
//        // Arrange
//        AnalyticsRequest request = new AnalyticsRequest();
//        request.setUserId("user456");
//
//        // Act
//        AnalyticsResponse response = analyticsService.generateAnalytics(request);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals("user456", response.getUserId());
//        assertEquals(0, response.getCompletedTaskCount());
//        assertEquals(0, response.getPendingTaskCount());
//        assertEquals(0, response.getTotalTaskCount());
//        assertEquals(0.0, response.getCompletedTaskPercentage());
//        assertEquals(0.0, response.getPendingTaskPercentage());
//        assertEquals(0.0, response.getCompletedToPendingRatio());
//    }
//
//    @Test
//    void testGetAnalyticsByUserId() {
//        createTaskForUser("user123", "Complete the project", "This task involves completing the project.", TaskCategory.WORK, TaskStatus.COMPLETED);
//        createTaskForUser("user123", "Start the new module", "This task involves starting a new module.", TaskCategory.WORK, TaskStatus.IN_PROGRESS);
//
//        AnalyticsRequest request = new AnalyticsRequest();
//        request.setUserId("user123");
//        analyticsService.generateAnalytics(request);
//
//        AnalyticsResponse response = analyticsService.getAnalyticsByUserId("user123");
//
//        assertNotNull(response);
//        assertEquals("user123", response.getUserId());
//        assertEquals(1, response.getCompletedTaskCount());
//        assertEquals(1, response.getPendingTaskCount());
//        assertEquals(2, response.getTotalTaskCount());
//        assertEquals(50.0, response.getCompletedTaskPercentage());
//        assertEquals(50.0, response.getPendingTaskPercentage());
//        assertEquals(1.0, response.getCompletedToPendingRatio());
//    }
//
//    @Test
//    void testGetAnalyticsByUserId_ThrowsException() {
//        // Arrange
//        String nonExistentUserId = "user789";
//
//        // Act & Assert
//        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
//            analyticsService.getAnalyticsByUserId(nonExistentUserId);
//        });
//        assertEquals("Analytics not found for userId: user789", exception.getMessage());
//    }
//
//    private void createTaskForUser(String userId, String title, String description, TaskCategory category, TaskStatus status) {
//        CreateTaskRequest taskRequest = new CreateTaskRequest();
//        taskRequest.setUserId(userId);
//        taskRequest.setTitle(title);
//        taskRequest.setDescription(description);
//        taskRequest.setCategory(category);
//        taskRequest.setStatus(status);
//        taskRequest.setDeadline(LocalDateTime.now().plusDays(1));
//        taskService.createTask(taskRequest);
//    }
//
//}
