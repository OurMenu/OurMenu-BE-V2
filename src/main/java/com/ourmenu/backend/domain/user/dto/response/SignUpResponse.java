package com.ourmenu.backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SignUpResponse {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiredAt;

    private Long refreshTokenExpiredAt;
}
