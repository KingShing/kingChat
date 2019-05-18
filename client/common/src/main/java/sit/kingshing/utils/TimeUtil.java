package sit.kingshing.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtil {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime UDateToLocalDateTime(Date date) {
        if (date == null) return null;
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

}
