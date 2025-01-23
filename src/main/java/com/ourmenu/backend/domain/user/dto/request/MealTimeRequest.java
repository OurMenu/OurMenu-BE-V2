package com.ourmenu.backend.domain.user.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class MealTimeRequest {
    ArrayList<Integer> mealTime;
}
