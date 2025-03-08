package com.ourmenu.backend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoExistenceResponse {

    private boolean isExistUser;

    public static KakaoExistenceResponse from(boolean isExistUser){
        return KakaoExistenceResponse.builder()
                .isExistUser(isExistUser)
                .build();
    }
}
