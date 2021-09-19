package ng.com.codetrik.notepad.exceptions;
/*
 @Author Hamzat Habibllahi
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class Error {
    private String message;
    private HttpStatus responseCode;
}
