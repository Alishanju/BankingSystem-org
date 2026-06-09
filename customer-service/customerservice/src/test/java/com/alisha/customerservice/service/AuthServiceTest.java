package com.alisha.customerservice.service;

import com.alisha.customerservice.dto.LoginRequest;
import com.alisha.customerservice.entity.Customer;
import com.alisha.customerservice.repository.CustomerRepository;
import com.alisha.customerservice.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest();

        request.setUsername("aasif");
        request.setPassword("password");

        Customer customer = Customer.builder()
                .username("aasif")
                .password("encoded")
                .role("USER")
                .build();

        when(repository.findByUsername("aasif"))
                .thenReturn(Optional.of(customer));

        when(passwordEncoder.matches(
                "password",
                "encoded"))
                .thenReturn(true);

        when(jwtService.generateToken(
                "aasif",
                "USER"))
                .thenReturn("jwt-token");

        String token = authService.login(request);

        assertEquals(
                "jwt-token",
                token);
    }
}