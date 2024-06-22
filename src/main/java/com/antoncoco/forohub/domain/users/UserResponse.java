package com.antoncoco.forohub.domain.users;

public record UserResponse(
        String username,
        String email,
        String biography
){
}
