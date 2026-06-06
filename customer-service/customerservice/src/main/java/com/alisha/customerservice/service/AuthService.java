package com.alisha.customerservice.service;

import com.alisha.customerservice.dto.LoginRequest;
import com.alisha.customerservice.dto.RegisterRequest;
import com.alisha.customerservice.entity.Customer;
import com.alisha.customerservice.repository.CustomerRepository;
import com.alisha.customerservice.security.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {

        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        repository.save(customer);

        return "User Registered Successfully";
    }

public String login(LoginRequest request) {

    Customer customer =
            repository.findByUsername(
                    request.getUsername())
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    boolean valid =
            passwordEncoder.matches(
                    request.getPassword(),
                    customer.getPassword());

    if (!valid) {
        throw new RuntimeException(
                "Invalid credentials");
    }

    return jwtService.generateToken(
            customer.getUsername(),
            customer.getRole());
}
}