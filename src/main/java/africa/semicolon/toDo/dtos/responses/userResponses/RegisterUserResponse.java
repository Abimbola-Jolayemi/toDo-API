package africa.semicolon.toDo.dtos.responses.userResponses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterUserResponse {
    private String id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private boolean isLoggedIn;
}
