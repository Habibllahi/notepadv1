package ng.com.codetrik.notepad.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.DayOfWeek;
import java.time.Month;

@Data
@AllArgsConstructor
public class DateDTO {
    private DayOfWeek dayOfWeek;
    private int dayOfYear;
    private int dayOfMonth;
    private Month month;
    private int year;
    private int hour;
    private int minute;
}
