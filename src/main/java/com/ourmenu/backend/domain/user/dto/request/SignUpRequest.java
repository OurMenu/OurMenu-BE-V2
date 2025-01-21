package com.ourmenu.backend.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class SignUpRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotEmpty
    private ArrayList<String> mealTime;

    @NotBlank
    private String signInType;
}
