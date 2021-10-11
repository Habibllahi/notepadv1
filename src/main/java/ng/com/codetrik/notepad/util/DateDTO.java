/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Month;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DateDTO {
    private DayOfWeek dayOfWeek;
    private int dayOfYear;
    private int dayOfMonth;
    private Month month;
    private int year;
    private int hour;
    private int minute;
}
