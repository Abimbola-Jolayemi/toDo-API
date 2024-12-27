package africa.semicolon.toDo.services.implementations;

import africa.semicolon.toDo.data.models.User;
import africa.semicolon.toDo.data.repositories.UserRepository;
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
import africa.semicolon.toDo.services.passwordHashing.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordHash passwordHash;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        if (!isValidEmail(request.getEmail())) {
            throw new InvalidEmailException("Invalid email format");
        }

        if (!isValidPassword(request.getPassword())) {
            throw new InvalidPasswordException("Password must be at least 8 characters long and contain an uppercase letter, a digit, and a special character.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserExistException("Email already exists");
        }

        String hashedPassword = passwordHash.hashPassword(request.getPassword());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);

        RegisterUserResponse response = new RegisterUserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setLoggedIn(savedUser.isLoggedIn());

        return response;
    }


    private boolean isValidEmail(String email) {
        String validEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(validEmail);
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        User user = userOptional.orElseThrow(() -> new InvalidEmailException("Email not registered"));

        if (!passwordHash.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }

        if (user.isLoggedIn()) {
            throw new UserAlreadyLoggedInException("User is already logged in");
        }

        user.setLoggedIn(true);
        userRepository.save(user);

        // Prepare the response
        LoginResponse response = new LoginResponse();
        response.setEmail(loginRequest.getEmail());
        response.setLoggedIn(true);
        response.setMessage("Login successful");
        return response;
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        User user = userRepository.findByEmail(logoutRequest.getEmail()).orElseThrow(() ->
                new InvalidEmailException("User not found"));
        if (!user.isLoggedIn()) {
            throw new InvalidEmailException("User is not logged in");
        }
        user.setLoggedIn(false);
        userRepository.save(user);

        LogoutResponse response = new LogoutResponse();
        response.setEmail(logoutRequest.getEmail());
        response.setLoggedIn(false);
        response.setMessage("Logout successful");
        return response;
    }

    @Override
    public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) {
        User user = userRepository.findByEmail(deleteUserRequest.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
        DeleteUserResponse response = new DeleteUserResponse();
        response.setMessage("User deleted successfully");
        return response;
    }

    @Override
    public String deleteAll() {
        userRepository.deleteAll();
        return "All users are deleted succesfully";
    }
}
