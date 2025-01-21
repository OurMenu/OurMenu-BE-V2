package com.ourmenu.backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Instant refreshTokenExpiredAt;

    public static TokenDto of(String accessToken, String refreshToken, Instant refreshTokenExpiredAt){
        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpiredAt(refreshTokenExpiredAt)
                .build();
    }
}
