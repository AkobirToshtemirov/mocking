package com.akobir.mocking.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthUserCriteria(
        @NotBlank
        String username,

        @NotBlank
        String email
) {
}
