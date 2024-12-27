package africa.semicolon.toDo.exceptions;

public class UserAlreadyLoggedInException extends RuntimeException {
    public UserAlreadyLoggedInException() {
        super("User is already logged in");
    }
    public UserAlreadyLoggedInException(String message) {
        super(message);
    }
    public UserAlreadyLoggedInException(String message, Throwable cause) {
        super(message, cause);
    }
}
