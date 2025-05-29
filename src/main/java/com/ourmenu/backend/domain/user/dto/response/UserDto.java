package com.ourmenu.backend.domain.user.dto.response;

import com.ourmenu.backend.domain.user.domain.User;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {

    private String email;
    private String signInType;
    private List<MealTimeDto> mealTimeList;
    private String announcementUrl;
    private String customerServiceUrl;
    private String appReviewUrl;

    public static UserDto of(User user, List<MealTimeDto> mealTimes) {
        return UserDto.builder()
                .email(user.getEmail())
                .signInType(user.getSignInType().name())
                .mealTimeList(mealTimes)
                .announcementUrl("")
                .customerServiceUrl("")
                .appReviewUrl("")
                .build();
    }
}
