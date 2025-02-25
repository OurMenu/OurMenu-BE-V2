package com.ourmenu.backend.domain.user.application;

import com.ourmenu.backend.domain.user.dao.MealTimeRepository;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.domain.MealTime;
import com.ourmenu.backend.domain.user.dto.request.MealTimeRequest;
import com.ourmenu.backend.domain.user.exception.InvalidMealTimeCountException;
import com.ourmenu.backend.domain.user.util.TimeUtil;
import java.time.LocalDateTime;
import java.util.Comparator;
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
     * @return
     */
    public long getNextUpdateMinute(Long userId) {
        List<MealTime> mealTimes = mealTimeRepository.findAllByUserId(userId)
                .stream()
                .sorted(Comparator.comparing(MealTime::getMealTime))
                .toList();

        return 0;
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
     * 식시시간을 시간 타입을 변환하여 저장한다.
     *
     * @param timeInteger
     * @param userId
     * @return
     */
    private MealTime saveMealTime(int timeInteger, Long userId) {
        LocalDateTime time = TimeUtil.of(timeInteger);
        MealTime mealTime = MealTime.builder()
                .userId(userId)
                .mealTime(time)
                .build();
        return mealTimeRepository.save(mealTime);
    }

}
