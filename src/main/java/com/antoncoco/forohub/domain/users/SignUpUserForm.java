package com.antoncoco.forohub.domain.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpUserForm(
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}
