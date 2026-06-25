package com.alisha.customerservice.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final KeyLoader keyLoader;

    @Value("${jwt.expiration}")
    private long expiration;

    private JwtParser parser;

    @PostConstruct
    public void init() {

        parser = Jwts.parser()
                .verifyWith(keyLoader.getPublicKey())
                .build();
    }

    public String generateToken(
            String username,
            String role) {

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
    }

}