package com.ourmenu.backend.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthUnlinkRequest {
    private String kakaoUserId;
    private String email;
}
