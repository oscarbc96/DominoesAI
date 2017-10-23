package exception;

public class BadInputException extends Throwable {
    public BadInputException() {
        super("Invalid input");
    }
}
