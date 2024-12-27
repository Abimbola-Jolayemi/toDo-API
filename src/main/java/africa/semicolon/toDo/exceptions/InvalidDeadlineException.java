package africa.semicolon.toDo.exceptions;

public class InvalidDeadlineException extends RuntimeException {

    public InvalidDeadlineException(String message) {
        super(message);
    }

    public InvalidDeadlineException(String message, Throwable cause) {
        super(message, cause);
    }
}
