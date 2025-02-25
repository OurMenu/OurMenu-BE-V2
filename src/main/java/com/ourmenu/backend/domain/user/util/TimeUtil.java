package com.ourmenu.backend.domain.user.util;

import java.time.Duration;
import java.time.LocalTime;

public class TimeUtil {

    private static final int UPCOMING_GAP = 2;

    private TimeUtil() {
    }

    /**
     * 시간을 LocalTime으로 변환한다.
     *
     * @param time
     * @return
     */
    public static LocalTime of(int time) {
        String timeStr = String.format("%04d", time);

        int hour = Integer.parseInt(timeStr.substring(0, 2));
        int minute = Integer.parseInt(timeStr.substring(2, 4));

        return LocalTime.of(hour, minute);
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
     * 아침 시간을 반환한다.
     *
     * @return
     */
    public static LocalTime getStartTime() {
        return LocalTime.of(7, 0);
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
