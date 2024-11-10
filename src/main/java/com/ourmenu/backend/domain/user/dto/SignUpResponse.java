package com.ourmenu.backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class SignUpResponse {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiredAt;

    private Long refreshTokenExpiredAt;
}
