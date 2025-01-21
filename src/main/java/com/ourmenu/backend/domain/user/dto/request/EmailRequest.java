package com.ourmenu.backend.domain.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailRequest {
    private String email;
}
