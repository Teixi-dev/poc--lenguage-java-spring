package teixi.dev.poc.shared.infrastructure.services;

import java.util.Calendar;
import java.util.Date;

public class TimeStampGenerator {

    public static Date now() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
