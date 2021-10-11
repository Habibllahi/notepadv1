/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@CrossOrigin(origins = "http://localhost:4200")
public class ExceptionControlAdviser {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> userAlreadyExist(Exception exception){
        return new ResponseEntity<>(new Error("User already exist", HttpStatus.FORBIDDEN),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<?> noSuchEntityException(Exception exception){
        return new ResponseEntity<>(new Error("No such entity found", HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementException(Exception exception){
        return new ResponseEntity<>(new Error("No such entity found", HttpStatus.EXPECTATION_FAILED),HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(SaveUnsuccessfulException.class)
    public ResponseEntity<?> SaveUnsuccessfulException(Exception exception){
        return new ResponseEntity<>(new Error("save unsuccessful", HttpStatus.EXPECTATION_FAILED),HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> RequestUnsupportedException(Exception exception){
        return new ResponseEntity<>(new Error("HTTP request unsupported", HttpStatus.FORBIDDEN),HttpStatus.EXPECTATION_FAILED);
    }
}
