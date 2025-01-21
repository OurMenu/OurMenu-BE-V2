package com.ourmenu.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class MealTimeRequest {
    ArrayList<Integer> mealTime;
}
