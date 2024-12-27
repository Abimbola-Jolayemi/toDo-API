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
import africa.semicolon.toDo.exceptions.InvalidEmailException;
import africa.semicolon.toDo.exceptions.InvalidPasswordException;
import africa.semicolon.toDo.exceptions.UserExistException;
import africa.semicolon.toDo.exceptions.UserNotFoundException;
import africa.semicolon.toDo.services.interfaces.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServicesImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testThatUserCanRegister() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Abimbola");
        request.setEmail("abimbola@mail.com");
        request.setPassword("Abimbola123$");
        RegisterUserResponse response = userService.registerUser(request);
        assertEquals("Abimbola", response.getName());
        assertEquals("abimbola@mail.com", response.getEmail());
    }

    @Test
    public void testThatTwoUsersCannotRegisterWithTheSameEmail() {
        RegisterUserRequest firstRequest = new RegisterUserRequest();
        firstRequest.setName("Abimbola");
        firstRequest.setEmail("abimbola@mail.com");
        firstRequest.setPassword("Abimbola123$");
        RegisterUserResponse firstResponse = userService.registerUser(firstRequest);
        assertEquals("Abimbola", firstResponse.getName());
        assertEquals("abimbola@mail.com", firstResponse.getEmail());

        RegisterUserRequest secondRequest = new RegisterUserRequest();
        secondRequest.setName("Jolayemi");
        secondRequest.setEmail("abimbola@mail.com");
        secondRequest.setPassword("Abimbo123$");

        UserExistException exception = assertThrows(UserExistException.class, () -> {
            userService.registerUser(secondRequest);
        });
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    public void testThatUserCannotSignUpUsingAnInvalidEmailAddress(){
        RegisterUserRequest firstRequest = new RegisterUserRequest();
        firstRequest.setName("Abimbola");
        firstRequest.setEmail("invalid password");
        firstRequest.setPassword("Abimbola123$");
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {userService.registerUser(firstRequest);
        });
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    public void testThatUserCannotRegisterWithInvalidPassword() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Abimbola");
        request.setEmail("abimbola@mail.com");
        request.setPassword("123");

        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> {
            userService.registerUser(request);
        });
        assertEquals("Password must be at least 8 characters long and contain an uppercase letter, a digit, and a special character.", exception.getMessage());
    }

    @Test
    public void testThatRegisteredUserCanLogin(){
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Abimbola");
        request.setEmail("abimbola@mail.com");
        request.setPassword("Abimbola123$");
        RegisterUserResponse response = userService.registerUser(request);
        assertEquals("Abimbola", response.getName());
        assertEquals("abimbola@mail.com", response.getEmail());
    }

    @Test
    public void testThatUserCanLoginWithValidCredentials_userIsRegistered() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setName("Abimbola");
        registerRequest.setEmail("abimbola@mail.com");
        registerRequest.setPassword("Abimbola123$!");
        userService.registerUser(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("abimbola@mail.com");
        loginRequest.setPassword("Abimbola123$!");

        LoginResponse response = userService.loginUser(loginRequest);

        assertEquals("abimbola@mail.com", response.getEmail());
        assertEquals("Login successful", response.getMessage());

        User user = userRepository.findByEmail("abimbola@mail.com").orElseThrow();
        assertTrue(user.isLoggedIn());
    }

    @Test
    public void testThatUserCannotLoginWithUnregisteredEmail() {
        LoginRequest loginRequestWithInvalidEmail = new LoginRequest();
        loginRequestWithInvalidEmail.setEmail("unregistered@mail.com");
        loginRequestWithInvalidEmail.setPassword("Abimbola123$!");

        InvalidEmailException emailException = assertThrows(InvalidEmailException.class, () -> {
            userService.loginUser(loginRequestWithInvalidEmail);
        });
        assertEquals("Email not registered", emailException.getMessage());
    }

    @Test
    public void testThatUserCannotLoginWithIncorrectPassword() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setName("Abimbola");
        registerRequest.setEmail("abimbola@mail.com");
        registerRequest.setPassword("Abimbola123$");
        userService.registerUser(registerRequest);

        LoginRequest loginRequestWithInvalidPassword = new LoginRequest();
        loginRequestWithInvalidPassword.setEmail("abimbola@mail.com");
        loginRequestWithInvalidPassword.setPassword("WrongPassword123");

        InvalidPasswordException passwordException = assertThrows(InvalidPasswordException.class, () -> {
            userService.loginUser(loginRequestWithInvalidPassword);
        });
        assertEquals("Incorrect password", passwordException.getMessage());
    }

    @Test
    public void testThatUserCanLoginAndLogout() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setName("Abimbola");
        registerRequest.setEmail("abimbola@mail.com");
        registerRequest.setPassword("Abimbola123$");
        userService.registerUser(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("abimbola@mail.com");
        loginRequest.setPassword("Abimbola123$");
        userService.loginUser(loginRequest);
        User loggedInUser = userRepository.findByEmail("abimbola@mail.com").orElseThrow();
        assertTrue(loggedInUser.isLoggedIn());
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setEmail("abimbola@mail.com");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);
        assertEquals("Logout successful", logoutResponse.getMessage());
        User loggedOutUser = userRepository.findByEmail("abimbola@mail.com").orElseThrow();
        assertFalse(loggedOutUser.isLoggedIn());
    }



    @Test
    public void testThatUserCannotLogOutWhenUserIsNotLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setName("Abimbola");
        registerRequest.setEmail("abimbola@mail.com");
        registerRequest.setPassword("Abimbola123$");
        userService.registerUser(registerRequest);
        LogoutRequest request = new LogoutRequest();
        request.setEmail("abimbola@mail.com");
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            userService.logout(request);
        });
        assertEquals("User is not logged in", exception.getMessage());
    }

    @Test
    public void testThatUserCanBeDeleted() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setName("Abimbola");
        registerRequest.setEmail("abimbola@mail.com");
        registerRequest.setPassword("Abimbola123$!");
        userService.registerUser(registerRequest);
        User user = userRepository.findByEmail("abimbola@mail.com").orElseThrow();
        assertEquals("Abimbola", user.getName());
        DeleteUserRequest deleteRequest = new DeleteUserRequest();
        deleteRequest.setEmail("abimbola@mail.com");
        deleteRequest.setPassword("Abimbola123$!");
        DeleteUserResponse deleteResponse = userService.deleteUser(deleteRequest);
        Optional<User> deletedUser = userRepository.findByEmail("abimbola@mail.com");
        assertTrue(deletedUser.isEmpty());
    }

    @Test
    public void testThatDeletedUserCannotLogin() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setName("Abimbola");
        registerRequest.setEmail("abimbola@mail.com");
        registerRequest.setPassword("Abimbola123$!");
        userService.registerUser(registerRequest);
        DeleteUserRequest deleteRequest = new DeleteUserRequest();
        deleteRequest.setEmail("abimbola@mail.com");
        userService.deleteUser(deleteRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("abimbola@mail.com");
        loginRequest.setPassword("Abimbola123$!");
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            userService.loginUser(loginRequest);
        });
        assertEquals("Email not registered", exception.getMessage());
    }

    @Test
    public void testThatADeletedUserCanRe_Register(){
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setName("Abimbola");
        registerRequest.setEmail("abimbola@mail.com");
        registerRequest.setPassword("Abimbola123$!");
        userService.registerUser(registerRequest);
        User user = userRepository.findByEmail("abimbola@mail.com").orElseThrow();
        assertEquals("Abimbola", user.getName());
        DeleteUserRequest deleteRequest = new DeleteUserRequest();
        deleteRequest.setEmail("abimbola@mail.com");
        deleteRequest.setPassword("Abimbola123$!");
        DeleteUserResponse deleteResponse = userService.deleteUser(deleteRequest);
        Optional<User> deletedUser = userRepository.findByEmail("abimbola@mail.com");
        assertTrue(deletedUser.isEmpty());

        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Abimbola");
        request.setEmail("abimbola@mail.com");
        request.setPassword("Abimbola123$!");
        RegisterUserResponse response = userService.registerUser(request);
        assertEquals("Abimbola", response.getName());
        assertEquals("abimbola@mail.com", response.getEmail());
    }
}
