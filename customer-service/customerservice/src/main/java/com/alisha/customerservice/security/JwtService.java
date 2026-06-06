package com.alisha.customerservice.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final KeyLoader keyLoader;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(
            String username,
            String role) {

        try {

            return Jwts.builder()
                    .subject(username)
                    .claim("role", role)
                    .issuedAt(new Date())
                    .expiration(
                            new Date(
                                    System.currentTimeMillis()
                                            + expiration))
                    .signWith(
                            keyLoader.getPrivateKey(),
                            Jwts.SIG.RS256)
                    .compact();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public String extractUsername(String token) {

        try {

            return Jwts.parser()
                    .verifyWith(keyLoader.getPublicKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTokenValid(String token) {

        try {

            Jwts.parser()
                    .verifyWith(keyLoader.getPublicKey())
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}