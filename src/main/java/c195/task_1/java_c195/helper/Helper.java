package c195.task_1.java_c195.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Helper {
    public static String convertLocalDateTimeToUTC(LocalDateTime dateValue) {
        ZonedDateTime zonedDateTime = dateValue.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime dateTimeUTC = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        String formattedUTC = dateTimeUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return formattedUTC;
    }

    public static ZonedDateTime convertLocalDateTimeToEST(LocalDateTime dateValue) {
        ZonedDateTime zonedDateTime = dateValue.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime dateTimeUTC = zonedDateTime.withZoneSameInstant(ZoneId.of("SystemV/EST5EDT"));

        return dateTimeUTC;
    }

    public static String getCurrentFormattedTimeUTC() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        String formattedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return formattedNow;
    }
}
