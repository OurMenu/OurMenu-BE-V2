package com.ourmenu.backend.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private ArrayList<Integer> mealTime;
    private String signInType;
}
