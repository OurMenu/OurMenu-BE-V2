package com.ourmenu.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;

public record MealTimeRequest(@NotEmpty ArrayList<String> mealTime) {
}
