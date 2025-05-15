package com.ourmenu.backend.domain.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReissueRequest {

    @NotBlank
    private String refreshToken;
}
