package com.ourmenu.backend.domain.user.util;

import java.time.Duration;
import java.time.LocalTime;

public class TimeUtil {

    private static final int UPCOMING_GAP = 2;

    private TimeUtil() {
    }

    /**
     * 파라미터 값이 현재 시간을 경과 했는지 검증한다
     *
     * @param time
     * @return 파라미터 값이 현재 시간을 경과 했으면 True
     */
    public static boolean isAfter(LocalTime time) {
        LocalTime now = LocalTime.now();

        if (time.isAfter(now)) {
            return true;
        }
        return false;
    }

    /**
     * UPCOMING_GAP 만큼 시간을 뺄셈한다.
     *
     * @param time
     * @return
     */
    public static LocalTime minusUpComingGap(LocalTime time) {
        return time.minusHours(UPCOMING_GAP);
    }

    /**
     * 현재 시간과 차이를 계산한다.
     *
     * @param time
     * @return
     */
    public static long getTimeDifference(LocalTime time) {
        LocalTime now = LocalTime.now();
        Duration duration = Duration.between(now, time);
        return duration.toMinutes();
    }

    /**
     * 현재 시간과 차이를 계산한다.
     *
     * @param time 파라미터 값이 내일이라고 가정한다.
     * @return
     */
    public static long getTimeDifferenceDayAfter(LocalTime time) {
        LocalTime now = LocalTime.now();
        Duration todayDuration = Duration.between(now, LocalTime.MAX);
        Duration tomorrowDuration = Duration.between(LocalTime.MIDNIGHT, now);

        return todayDuration.toMinutes() + tomorrowDuration.toMinutes();
    }

}
