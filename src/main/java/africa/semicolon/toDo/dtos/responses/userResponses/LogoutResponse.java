package africa.semicolon.toDo.dtos.responses.userResponses;

import lombok.Data;

@Data
public class LogoutResponse {
    private String email;
    private String message;
    private boolean isLoggedIn;
}
