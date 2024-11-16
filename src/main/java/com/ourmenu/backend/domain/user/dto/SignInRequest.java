package com.ourmenu.backend.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInRequest {
    private String email;
    private String password;
    private String signInType;
}
