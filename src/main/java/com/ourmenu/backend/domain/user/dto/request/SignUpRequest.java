package com.ourmenu.backend.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private ArrayList<Integer> mealTime;
    private String signInType;
}
