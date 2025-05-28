package com.ourmenu.backend.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ourmenu.backend.domain.user.domain.MealTime;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
@JsonIgnoreProperties({"after"})
public class MealTimeDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @ArraySchema(arraySchema = @Schema(example = "[\"12:00:00\"]"),
            schema = @Schema(type = "string", format = "time", example = "12:00:00"))
    private LocalTime mealTime;

    @JsonProperty("isAfter")
    private boolean isAfter;

    public static MealTimeDto of(MealTime mealTime, boolean isAfter) {
        return MealTimeDto.builder()
                .mealTime(mealTime.getMealTime())
                .isAfter(isAfter)
                .build();
    }
}
