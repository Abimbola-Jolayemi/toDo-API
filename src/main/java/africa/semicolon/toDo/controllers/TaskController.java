package africa.semicolon.toDo.controllers;

import africa.semicolon.toDo.data.models.enums.TaskCategory;
import africa.semicolon.toDo.data.models.enums.TaskStatus;
import africa.semicolon.toDo.data.repositories.TaskRepository;
import africa.semicolon.toDo.dtos.requests.taskRequests.*;
import africa.semicolon.toDo.dtos.responses.taskResponses.*;
import africa.semicolon.toDo.exceptions.InvalidDeadlineException;
import africa.semicolon.toDo.exceptions.MissingFieldException;
import africa.semicolon.toDo.exceptions.TaskNotFoundException;
import africa.semicolon.toDo.services.interfaces.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskServices taskservices;

    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest request) {
        try{
            CreateTaskResponse response = taskservices.createTask(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(MissingFieldException exception){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        } catch (InvalidDeadlineException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PostMapping("/updateTask")
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskRequest request) {
        try{
            UpdateTaskResponse response = taskservices.updateTask(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (TaskNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<?> deleteTask(@RequestBody DeleteTaskRequest request) {
        try{
            DeleteTaskResponse response = taskservices.deleteTask(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(TaskNotFoundException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<?> getAllTasks(@RequestBody GetAllTasksRequest request) {
        try {
            List<GetAllTasksResponse> response = taskservices.getAllTasks(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @DeleteMapping("/deleteAllTasks")
    public ResponseEntity<?> deleteAllTasks(@RequestBody DeleteAllTasksRequest request){
        try{
            DeleteAllTasksResponse response = taskservices.deleteAllTasks(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (TaskNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @PutMapping("/markAsCompleted")
    public ResponseEntity<?> markAsCompleted(@RequestBody MarkTaskAsCompletedRequest request) {
        try{
            MarkTaskAsCompletedResponse response = taskservices.markTaskAsCompleted(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (TaskNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PutMapping("/markAsInProgress")
    public ResponseEntity<?> markAsInProgress(@RequestBody MarkTaskAsInProgressRequest request) {
        try{
            MarkTaskAsInProgressResponse response = taskservices.markTaskAsInProgress(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (TaskNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @PutMapping("/markAsOverdue")
    public ResponseEntity<?> markAsOverdue(@RequestBody MarkTaskAsOverdueRequest request) {
        try{
            MarkTaskAsOverdueResponse response = taskservices.markTaskAsOverdue(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (TaskNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @GetMapping("/completedTasks")
    public ResponseEntity<?> getCompletedTasks(@RequestBody CompletedTaskRequest request) {
        try{
            List<CompletedTaskResponse> response = taskservices.getCompletedTask(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @GetMapping("/pendingTasks")
    public ResponseEntity<?> getPendingTasks(@RequestBody CompletedTaskRequest request) {
        try{
            List<CompletedTaskResponse> response = taskservices.getPendingTask(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @GetMapping("/inProgressTasks")
    public ResponseEntity<?> getInProgressTasks(@RequestBody CompletedTaskRequest request) {
        try{
            List<CompletedTaskResponse> response = taskservices.getInProgressTask(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }

    @GetMapping("/overdueTasks")
    public ResponseEntity<?> getOverdueTasks(@RequestBody CompletedTaskRequest request) {
        try{
            List<CompletedTaskResponse> response = taskservices.getOverdueTask(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }



//    @GetMapping("/searchTasks")
//    public ResponseEntity<?> searchTask(
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String description,
//            @RequestParam(required = false) LocalDateTime deadline,
//            @RequestParam(required = false) TaskStatus status,
//            @RequestParam(required = false) TaskCategory category
//    ) {
//        try {
//            System.out.println("Title: " + title);
//            System.out.println("Description: " + description);
//            System.out.println("Status: " + status);
//            System.out.println("Category: " + category);
//
//            SearchTaskRequest searchRequest = new SearchTaskRequest();
//            searchRequest.setTitle(title);
//            searchRequest.setDescription(description);
//            searchRequest.setDeadline(deadline);
//            searchRequest.setStatus(status);
//            searchRequest.setCategory(category);
//
//            List<SearchTaskResponse> response = taskservices.searchTasks(searchRequest);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (TaskNotFoundException exception) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
//        } catch (IllegalArgumentException exception) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//        } catch (Exception exception) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
//        }
//    }

}
