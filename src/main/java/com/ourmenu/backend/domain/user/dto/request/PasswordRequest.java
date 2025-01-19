package com.ourmenu.backend.domain.user.dto.request;


import jakarta.validation.constraints.NotBlank;

public record PasswordRequest(@NotBlank String password,@NotBlank String newPassword) {
}
