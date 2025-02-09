package com.ourmenu.backend.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDto {
    private Boolean isExist;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String refreshTokenExpiredAt;

    public static TokenDto of(String accessToken, String refreshToken, Instant refreshTokenExpiredAt){
        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpiredAt(refreshTokenExpiredAt.toString())
                .build();
    }

    public static TokenDto of(boolean isExist, String accessToken, String refreshToken, Instant refreshTokenExpiredAt){
        return TokenDto.builder()
                .isExist(isExist)
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpiredAt(refreshTokenExpiredAt.toString())
                .build();
    }
}
