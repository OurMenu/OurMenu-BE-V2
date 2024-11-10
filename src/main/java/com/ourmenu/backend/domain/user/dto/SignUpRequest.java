package com.ourmenu.backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private ArrayList<String> mealTime;
    private String signInType;
}
