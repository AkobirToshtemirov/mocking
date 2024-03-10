package com.akobir.mocking.dto;

public record AuthUserGetDTO(
        Long id,
        String username,
        String email
) {
}
