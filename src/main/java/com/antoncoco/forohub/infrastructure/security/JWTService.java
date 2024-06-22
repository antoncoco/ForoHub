package com.antoncoco.forohub.infrastructure.security;

import com.antoncoco.forohub.domain.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    @Getter
    private long expirationTime;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            return JWT.create()
                    .withIssuer("ForoHub")
                    .withSubject(user.getUsername())
                    .withClaim("username", user.getName())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }
    }

    public String getSubjectFromToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            decodedJWT = JWT.require(algorithm)
                    .withIssuer("ForoHub")
                    .build()
                    .verify(token);
            if(decodedJWT.getSubject() == null) {
                throw new JWTVerificationException("JWT is missing subject");
            }
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException(exception);
        }
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("ForoHub")
                    .build();
            decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject().equals(userDetails.getUsername())
                    && !decodedJWT.getExpiresAt().before(new Date(System.currentTimeMillis()));
        } catch (JWTVerificationException exception){
            throw new RuntimeException(exception);
        }
    }

}
