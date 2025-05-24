package com.ourmenu.backend.domain.user.dto.response;

import com.ourmenu.backend.domain.user.domain.MealTime;
import com.ourmenu.backend.domain.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;

import com.ourmenu.backend.domain.user.util.TimeUtil;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {

    private String email;
    private String signInType;
    private List<MealTimeDto> mealTimeList;

    public static UserDto of(User user, List<MealTimeDto> mealTimes) {
        return UserDto.builder()
                .email(user.getEmail())
                .signInType(user.getSignInType().name())
                .mealTimeList(mealTimes)
                .build();
    }
}
