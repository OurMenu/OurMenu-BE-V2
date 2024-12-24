package com.ourmenu.backend.domain.user.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;

public record MealTimeRequest(@NotEmpty ArrayList<String> mealTime) {
}
