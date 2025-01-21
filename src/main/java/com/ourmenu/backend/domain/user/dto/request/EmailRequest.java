package com.ourmenu.backend.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailRequest {

    @Email
    private String email;
}
