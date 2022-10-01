package cybersoft.java18.crm.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class TimeUtil {
    public static LocalDateTime getTimeFromStr (String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDate.parse(time, formatter).atStartOfDay();
        return dateTime;
    }
}
