package com.akobir.mocking.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthUserUpdateDTO(
        Long id,

        @NotBlank
        String username,

        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
