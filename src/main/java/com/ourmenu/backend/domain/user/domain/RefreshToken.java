package com.ourmenu.backend.domain.user.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 30)
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @Indexed
    private String email;

    @Indexed
    private SignInType signInType;

    public RefreshToken(String token, String email, SignInType signInType) {
        this.refreshToken = token;
        this.email = email;
        this.signInType = signInType;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }

}
