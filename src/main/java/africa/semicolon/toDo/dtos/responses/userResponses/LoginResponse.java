package africa.semicolon.toDo.dtos.responses.userResponses;

import lombok.Data;

@Data
public class LoginResponse {
    private String userId;
    private String email;
    private boolean isLoggedIn;
    private String message;
}
