package ng.com.codetrik.notepad.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Month;

@Data
@Component
public class DateDTO {
    private DayOfWeek dayOfWeek;
    private int dayOfYear;
    private int dayOfMonth;
    private Month month;
    private int year;
    private int hour;
    private int minute;
}
