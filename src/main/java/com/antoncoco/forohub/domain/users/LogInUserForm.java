package com.antoncoco.forohub.domain.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LogInUserForm(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
){
}
