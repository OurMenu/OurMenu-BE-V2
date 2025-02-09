package com.ourmenu.backend.domain.user.dto.response;

import com.ourmenu.backend.domain.user.domain.MealTime;
import com.ourmenu.backend.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class UserDto {

    private String email;
    private String signInType;
    private List<Integer> mealTime;

    public static UserDto of(User user, List<MealTime> mealTimes){
        return UserDto.builder()
                .email(user.getEmail())
                .signInType(user.getSignInType().name())
                .mealTime(mealTimes.stream()
                        .map(MealTime::getMealTime)
                        .collect(Collectors.toList()))
                .build();
    }
}
