package com.ourmenu.backend.domain.user.dto.response;

import com.ourmenu.backend.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {

    private Long userId;
    private String email;
    private String signInType;

    public static UserDto of(User user){
        return UserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .signInType(user.getSignInType().name())
                .build();
    }
}
