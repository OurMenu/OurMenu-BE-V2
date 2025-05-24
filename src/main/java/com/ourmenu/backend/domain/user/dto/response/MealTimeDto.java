package com.ourmenu.backend.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ourmenu.backend.domain.user.domain.MealTime;
import com.ourmenu.backend.domain.user.util.TimeUtil;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties({"after"})
public class MealTimeDto {

    private Integer mealTime;

    @JsonProperty("isAfter")
    private boolean isAfter;

    public static MealTimeDto of(MealTime mealTime, boolean isAfter) {
        return MealTimeDto.builder()
                .mealTime(TimeUtil.toInteger(mealTime.getMealTime()))
                .isAfter(isAfter)
                .build();
    }
}
