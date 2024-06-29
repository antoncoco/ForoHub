package com.antoncoco.forohub.domain.users;

public record UserResponse(
        String username,
        String email,
        String biography
){
    public UserResponse(User user) {
        this(user.getName(), user.getEmail(), user.getBiography());
    }
}
