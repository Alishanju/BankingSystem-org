package com.alisha.customerservice.service;

import com.alisha.customerservice.dto.LoginRequest;
import com.alisha.customerservice.dto.LoginResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

        @Mock
        private CustomerRepository repository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Mock
        private JwtService jwtService;

        @Mock
        private RabbitMQPublisher rabbitMQPublisher;

        @InjectMocks
        private AuthService authService;

        @Test
        void shouldLoginSuccessfully() {

                LoginRequest request = new LoginRequest();

                request.setUsername("aasif");
                request.setPassword("password");

                Customer customer = Customer.builder()
                                .id(2L)
                                .username("aasif")
                                .email("aasif@gmail.com")
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

                LoginResponse response = authService.login(request);

                assertNotNull(response);

                assertEquals(
                                "jwt-token",
                                response.getToken());

                assertEquals(
                                "aasif",
                                response.getUser().getUsername());

                assertEquals(
                                2L,
                                response.getUser().getId());

                assertEquals(
                                "USER",
                                response.getUser().getRole());
        }
}