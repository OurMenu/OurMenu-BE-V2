package com.ourmenu.backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class KakaoExistenceResponse {

    private boolean isExistUser;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Instant refreshTokenExpiredAt;

    public static KakaoExistenceResponse from(boolean isExistUser){
        return KakaoExistenceResponse.builder()
                .isExistUser(isExistUser)
                .build();
    }

    public static KakaoExistenceResponse from(boolean isExistUser, TokenDto tokenDto){
        return KakaoExistenceResponse.builder()
                .isExistUser(isExistUser)
                .grantType(tokenDto.getGrantType())
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .refreshTokenExpiredAt(tokenDto.getRefreshTokenExpiredAt())
                .build();
    }
}
