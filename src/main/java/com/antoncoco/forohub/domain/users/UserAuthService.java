package com.antoncoco.forohub.domain.users;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(SignUpUserForm signUpUserForm) {
        User user = User.builder()
                .name(signUpUserForm.username())
                .password(passwordEncoder.encode(signUpUserForm.password()))
                .email(signUpUserForm.email())
                .biography(null)
                .build();
        return userRepository.save(user);
    }

    public User authenticate(LogInUserForm logInUserForm) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInUserForm.email(),
                        logInUserForm.password()
                )
        );
        return this.userRepository.findByEmail(logInUserForm.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
