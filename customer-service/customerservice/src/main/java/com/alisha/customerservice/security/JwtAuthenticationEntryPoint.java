package com.alisha.customerservice.security;

import com.alisha.customerservice.dto.AuthenticationErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) {

        try {

            log.warn(
                    "Unauthorized access attempt. URI={} IP={}",
                    request.getRequestURI(),
                    request.getRemoteAddr());

            AuthenticationErrorResponse error = new AuthenticationErrorResponse(
                    LocalDateTime.now(),
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized",
                    "Valid JWT token is required",
                    request.getRequestURI());

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            response.setContentType(
                    "application/json");

            objectMapper.writeValue(
                    response.getOutputStream(),
                    error);

        } catch (Exception ex) {

            log.error(
                    "Failed to write authentication error response",
                    ex);
        }
    }
}