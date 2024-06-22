package com.antoncoco.forohub.domain.users;

public record LogInResponse(
        String token,
        long expiresIn
) {
}
