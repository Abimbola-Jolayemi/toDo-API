package africa.semicolon.toDo.dtos.requests.userRequests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
