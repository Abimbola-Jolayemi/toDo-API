package africa.semicolon.toDo.dtos.responses.userResponses;

import lombok.Data;

@Data
public class DeleteUserResponse {
    private String email;
    private String message;
}
