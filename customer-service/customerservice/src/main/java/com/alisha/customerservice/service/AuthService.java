package com.alisha.customerservice.service;

import com.alisha.customerservice.dto.LoginRequest;
import com.alisha.customerservice.dto.LoginResponse;
import com.alisha.customerservice.dto.LoginUserResponse;
import com.alisha.customerservice.dto.RegisterRequest;
import com.alisha.customerservice.entity.Customer;
import com.alisha.customerservice.repository.CustomerRepository;
import com.alisha.customerservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.alisha.customerservice.event.CustomerCreatedEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

        private final CustomerRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final RabbitMQPublisher rabbitMQPublisher;

        public String register(RegisterRequest request) {

                Customer customer = Customer.builder()
                                .name(request.getName())
                                .email(request.getEmail())
                                .phone(request.getPhone())
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role("USER")
                                .build();
                log.info("Registering user {}", request.getUsername());

                Customer savedCustomer = repository.save(customer);

                rabbitMQPublisher.publishCustomerCreated(

                                CustomerCreatedEvent.builder()
                                                .customerId(
                                                                savedCustomer.getId())
                                                .username(
                                                                savedCustomer.getUsername())
                                                .email(
                                                                savedCustomer.getEmail())
                                                .build());

                log.info("User registered successfully {}",
                                request.getUsername());

                return "User Registered Successfully";
        }

    public LoginResponse login(LoginRequest request) {

                Customer customer = repository.findByUsername(
                                request.getUsername())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                log.info("Login request received for {}",
                                request.getUsername());
                boolean valid = passwordEncoder.matches(
                                request.getPassword(),
                                customer.getPassword());

                if (!valid) {
                        log.error("Invalid credentials for {}",
                                        request.getUsername());
                        throw new RuntimeException(
                                        "Invalid credentials");
                }
                log.info("Login successful for {}",
                                request.getUsername());

                String token = jwtService.generateToken(
        customer.getUsername(),
        customer.getRole());

LoginUserResponse user = new LoginUserResponse(

        customer.getId(),

        customer.getUsername(),

        customer.getEmail(),

        customer.getRole());

return new LoginResponse(

        token,

        user);
}
}