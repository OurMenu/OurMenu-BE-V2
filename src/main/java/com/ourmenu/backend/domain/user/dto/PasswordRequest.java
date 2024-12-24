package com.ourmenu.backend.domain.user.dto;


import jakarta.validation.constraints.NotBlank;

public record PasswordRequest(@NotBlank String password,@NotBlank String newPassword) {
}
