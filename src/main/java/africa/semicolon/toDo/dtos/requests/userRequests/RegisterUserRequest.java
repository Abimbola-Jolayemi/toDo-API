package africa.semicolon.toDo.dtos.requests.userRequests;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String name;
    private String email;
    private String password;
}
