package wolox.training.exceptions;

public class BookAlreadyOwnedException extends RuntimeException {

    public BookAlreadyOwnedException(String message, Throwable cause) {
        super(message, cause);
    }

}
