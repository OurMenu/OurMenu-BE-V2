package com.ourmenu.backend.domain.user.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtil {

    private static final int UPCOMING_GAP = 2;

    private TimeUtil() {
    }

    /**
     * 시간을 LocalDateTime으로 변환한다.
     *
     * @param time
     * @return
     */
    public static LocalDateTime of(int time) {
        String timeStr = String.format("%04d", time);

        int hour = Integer.parseInt(timeStr.substring(0, 2));
        int minute = Integer.parseInt(timeStr.substring(2, 4));

        return LocalDateTime.now().withHour(hour).withMinute(minute).withSecond(0).withNano(0);
    }

    /**
     * 파라미터의 값이 다음 추천 시간에 적당한지 점증한다. 현재 시간이 파라미터와 UPCOMING_GAP 차이가 나는지 검증한다.
     *
     * @param time
     * @return 파라미터 값이 현재 시간보다 뒤이면 True
     */
    public static boolean isUpcoming(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        time = LocalDateTime.now().withHour(time.getHour()).minusMinutes(time.getMinute());
        if (time.isAfter(now)) {
            return true;
        }
        return false;
    }

    /**
     * 아침 추천 시간을 반환한다.
     *
     * @return
     */
    public static LocalDateTime getStartTime() {
        return LocalDateTime.now().withHour(7).withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime plusUpComingGap(LocalDateTime time) {
        return time.plusHours(UPCOMING_GAP);
    }

    public static long getTimeDifference(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, time);
        return duration.toMinutes();
    }
}
