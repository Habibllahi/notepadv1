package ng.com.codetrik.notepad.exceptions;
/*
 @Author Hamzat Habibllahi
 */
public class SaveUnsuccessfulException extends RuntimeException{
    public SaveUnsuccessfulException() {
    }

    public SaveUnsuccessfulException(String message) {
        super(message);
    }

    public SaveUnsuccessfulException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveUnsuccessfulException(Throwable cause) {
        super(cause);
    }

    protected SaveUnsuccessfulException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
