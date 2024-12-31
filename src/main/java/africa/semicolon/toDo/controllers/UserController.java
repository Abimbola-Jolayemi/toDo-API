package africa.semicolon.toDo.controllers;

import africa.semicolon.toDo.dtos.requests.userRequests.DeleteUserRequest;
import africa.semicolon.toDo.dtos.requests.userRequests.LoginRequest;
import africa.semicolon.toDo.dtos.requests.userRequests.LogoutRequest;
import africa.semicolon.toDo.dtos.requests.userRequests.RegisterUserRequest;
import africa.semicolon.toDo.dtos.responses.userResponses.DeleteUserResponse;
import africa.semicolon.toDo.dtos.responses.userResponses.LoginResponse;
import africa.semicolon.toDo.dtos.responses.userResponses.LogoutResponse;
import africa.semicolon.toDo.dtos.responses.userResponses.RegisterUserResponse;
import africa.semicolon.toDo.exceptions.*;
import africa.semicolon.toDo.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import static javax.security.auth.callback.ConfirmationCallback.OK;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest request) {
        try {
            RegisterUserResponse response = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserExistException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch (InvalidEmailException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch (InvalidPasswordException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try{
            LoginResponse response = userService.loginUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(InvalidEmailException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch(InvalidPasswordException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch(UserAlreadyLoggedInException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody LogoutRequest request) {
        try{
            LogoutResponse response = userService.logout(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(InvalidEmailException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserRequest request) {
        try{
            DeleteUserResponse response = userService.deleteUser(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(UserNotFoundException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<?> deleteAllUsers(){
        try{
            String response = userService.deleteAll();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
