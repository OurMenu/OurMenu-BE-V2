package com.ourmenu.backend.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailResponse {
    private String code;
}
