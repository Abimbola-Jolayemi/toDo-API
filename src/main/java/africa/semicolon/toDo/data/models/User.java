package africa.semicolon.toDo.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean isLoggedIn = false;
}
