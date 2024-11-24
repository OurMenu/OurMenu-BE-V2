package com.ourmenu.backend.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailRequest {
    private String email;
}
