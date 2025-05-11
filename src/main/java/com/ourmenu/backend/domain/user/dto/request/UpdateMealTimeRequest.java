package com.ourmenu.backend.domain.user.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class UpdateMealTimeRequest {
    ArrayList<Integer> mealTime;
}
