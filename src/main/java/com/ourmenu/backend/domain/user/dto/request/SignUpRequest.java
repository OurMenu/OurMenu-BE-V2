package com.ourmenu.backend.domain.user.dto.request;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class SignUpRequest {
    private String email;
    private String password;
    private ArrayList<Integer> mealTime;
    private String signInType;
}
