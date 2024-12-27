package africa.semicolon.toDo.exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException() {
        super("User already exists");
    }

    public UserExistException(String message) {
        super(message);
    }

    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
