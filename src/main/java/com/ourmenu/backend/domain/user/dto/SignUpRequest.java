package com.ourmenu.backend.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
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
