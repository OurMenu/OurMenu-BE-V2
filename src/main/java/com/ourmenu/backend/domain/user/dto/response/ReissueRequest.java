package com.ourmenu.backend.domain.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReissueRequest {

    @NotBlank
    private String refreshToken;
}
