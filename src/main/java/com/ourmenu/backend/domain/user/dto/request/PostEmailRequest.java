package com.ourmenu.backend.domain.user.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostEmailRequest {
    private String email;
}
