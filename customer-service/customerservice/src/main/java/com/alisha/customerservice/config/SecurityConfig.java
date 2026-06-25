package com.alisha.customerservice.config;

import com.alisha.customerservice.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationEntryPoint authenticationEntryPoint;

        @Value("${jwt.public-key}")
        private Resource publicKeyResource;
        private final JwtConverter jwtConverter;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration config)
                        throws Exception {

                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http)
                        throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))
                                .exceptionHandling(exception -> exception.authenticationEntryPoint(
                                                authenticationEntryPoint))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/auth/register",
                                                                "/auth/login")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                                .oauth2ResourceServer(oauth -> oauth
                                                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

                return http.build();
        }

        @Bean
        public JwtDecoder jwtDecoder() throws Exception {

                String publicKeyContent = new String(
                                publicKeyResource.getInputStream().readAllBytes());

                publicKeyContent = publicKeyContent
                                .replace("-----BEGIN PUBLIC KEY-----", "")
                                .replace("-----END PUBLIC KEY-----", "")
                                .replaceAll("\\s+", "");

                byte[] decodedKey = Base64.getDecoder().decode(publicKeyContent);

                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);

                KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

                return NimbusJwtDecoder
                                .withPublicKey(publicKey)
                                .build();
        }
}