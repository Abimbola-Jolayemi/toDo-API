package africa.semicolon.toDo.services.interfaces;

import africa.semicolon.toDo.dtos.requests.userRequests.DeleteUserRequest;
import africa.semicolon.toDo.dtos.requests.userRequests.LoginRequest;
import africa.semicolon.toDo.dtos.requests.userRequests.LogoutRequest;
import africa.semicolon.toDo.dtos.requests.userRequests.RegisterUserRequest;
import africa.semicolon.toDo.dtos.responses.userResponses.DeleteUserResponse;
import africa.semicolon.toDo.dtos.responses.userResponses.LoginResponse;
import africa.semicolon.toDo.dtos.responses.userResponses.LogoutResponse;
import africa.semicolon.toDo.dtos.responses.userResponses.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
    LogoutResponse logout(LogoutRequest logoutRequest);
    DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest);
    String deleteAll();
}
