package com.ourmenu.backend.domain.user.application;

import com.ourmenu.backend.domain.user.dao.MealTimeRepository;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.domain.MealTime;
import com.ourmenu.backend.domain.user.dto.request.MealTimeRequest;
import com.ourmenu.backend.domain.user.exception.InvalidMealTimeCountException;
import com.ourmenu.backend.domain.user.util.TimeUtil;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MealTimeService {

    private final MealTimeRepository mealTimeRepository;

    /**
     * 다음 질문 생성 시간을 현재 시간 기준으로 조회한다.
     *
     * @param userId
     * @return 다음 갱신 시간 까지의 차이 분단위 반환
     */
    public long getNextUpdateMinute(Long userId) {

        LocalTime time = mealTimeRepository.findAllByUserId(userId)
                .stream()
                .map(MealTime::getMealTime)
                .map(TimeUtil::minusUpComingGap)
                .filter(TimeUtil::isAfter)
                .findFirst()
                .orElse(getFirstMealTime(userId));

        long timeDifference = TimeUtil.getTimeDifference(time);
        if (timeDifference < 0) {
            System.out.println("timeDifference = " + timeDifference);
            return TimeUtil.getTimeDifferenceDayAfter(time);
        }
        System.out.println("timeDifference = " + timeDifference);
        return timeDifference;
    }

    /**
     * 식사시간 여러개를 저장한다.
     *
     * @param mealTimes
     * @param userId
     * @return
     */
    @Transactional
    public List<MealTime> saveMealTimes(List<Integer> mealTimes, Long userId) {
        return mealTimes.stream()
                .map(mealTime -> saveMealTime(mealTime, userId))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MealTime> findAllByUserId(Long userId) {
        return mealTimeRepository.findAllByUserId(userId);
    }

    @Transactional
    public void changeMealTime(MealTimeRequest request, CustomUserDetails userDetails) {
        mealTimeRepository.deleteAllByUserId(userDetails.getId());

        List<MealTime> updatedMealTimes = saveMealTimes(request.getMealTime(), userDetails.getId());

        if (updatedMealTimes.isEmpty() || updatedMealTimes.size() > 4) {
            throw new InvalidMealTimeCountException();
        }

        mealTimeRepository.saveAll(updatedMealTimes);
    }

    /**
     * 첫번째 식사시간을 반환한다. 사전에 정의한 시간차만크 뺀다.
     *
     * @param userId
     * @return
     */
    private LocalTime getFirstMealTime(Long userId) {
        return mealTimeRepository.findAllByUserId(userId)
                .stream()
                .findFirst()
                .map(MealTime::getMealTime)
                .map(TimeUtil::minusUpComingGap)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * 식시시간을 시간 타입을 변환하여 저장한다.
     *
     * @param timeInteger
     * @param userId
     * @return
     */
    private MealTime saveMealTime(int timeInteger, Long userId) {
        LocalTime time = TimeUtil.of(timeInteger);
        MealTime mealTime = MealTime.builder()
                .userId(userId)
                .mealTime(time)
                .build();
        return mealTimeRepository.save(mealTime);
    }

}
