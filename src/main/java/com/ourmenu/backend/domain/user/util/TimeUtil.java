package com.ourmenu.backend.domain.user.util;

import java.time.LocalDateTime;

public class TimeUtil {

    private TimeUtil() {
    }

    public static LocalDateTime of(int time) {
        String timeStr = String.format("%04d", time);

        int hour = Integer.parseInt(timeStr.substring(0, 2));
        int minute = Integer.parseInt(timeStr.substring(2, 4));

        return LocalDateTime.now().withHour(hour).withMinute(minute).withSecond(0).withNano(0);
    }
}
