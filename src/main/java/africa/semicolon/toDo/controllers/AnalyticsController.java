//package africa.semicolon.toDo.controllers;
//
//import africa.semicolon.toDo.dtos.requests.analyticsRequests.CompletedTaskRequest;
//import africa.semicolon.toDo.dtos.responses.taskResponses.CompletedTaskResponse;
//import africa.semicolon.toDo.services.interfaces.AnalyticsServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/analytics")
//public class AnalyticsController {
//
//    @Autowired
//    private AnalyticsServices analyticsServices;
//
//    @GetMapping("/completedTasks")
//    public ResponseEntity<?> getCompletedTasks(@RequestBody CompletedTaskRequest request) {
//        try {
//            CompletedTaskRequest request = new CompletedTaskRequest();
//            request.setUserId(userId);
//            List<CompletedTaskResponse> response = analyticsServices.getCompletedTask(request);
//            return ResponseEntity.ok(response);
//        } catch (Exception exception) {
//            return ResponseEntity.status(500).body(exception.getMessage());
//        }
//    }
//
//}
