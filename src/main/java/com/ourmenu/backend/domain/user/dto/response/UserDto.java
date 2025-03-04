package com.ourmenu.backend.domain.user.dto.response;

import com.ourmenu.backend.domain.user.domain.MealTime;
import com.ourmenu.backend.domain.user.domain.User;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {

    private String email;
    private String signInType;
    private List<LocalTime> mealTime;

    public static UserDto of(User user, List<MealTime> mealTimes) {
        return UserDto.builder()
                .email(user.getEmail())
                .signInType(user.getSignInType().name())
                .mealTime(mealTimes.stream()
                        .map(MealTime::getMealTime)
                        .collect(Collectors.toList()))
                .build();
    }
}
