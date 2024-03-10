package com.akobir.mocking.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthUserCreateDTO(
        @NotBlank
        String username,

        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
