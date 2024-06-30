package com.antoncoco.forohub.controllers;

import com.antoncoco.forohub.domain.users.*;
import com.antoncoco.forohub.infrastructure.security.JWTService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private final JWTService jwtService;
    private final UserAuthService userAuthService;

    public UserAuthController(JWTService jwtService, UserAuthService userAuthService) {
        this.jwtService = jwtService;
        this.userAuthService = userAuthService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login into the app, getting an JWT with its expiration time")
    public ResponseEntity<LogInResponse> login(@RequestBody @Valid LogInUserForm logInUserForm) {
        User user = this.userAuthService.authenticate(logInUserForm);
        String jwt = this.jwtService.generateToken(user);
        return ResponseEntity.ok(
                new LogInResponse(
                        jwt,
                        this.jwtService.getExpirationTime()
                )
        );
    }

    @PostMapping("/signup")
    @Operation(summary = "Register a new user to the app")
    public ResponseEntity<UserResponse> signUp(@RequestBody @Valid SignUpUserForm signUpUserForm) {
        User registeredUser = this.userAuthService.register(signUpUserForm);
        return ResponseEntity.ok(new UserResponse(registeredUser.getName(), registeredUser.getEmail(), registeredUser.getBiography()));
    }
}
