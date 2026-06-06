package com.alisha.customerservice.controller;

import com.alisha.customerservice.dto.LoginRequest;
import com.alisha.customerservice.dto.LoginResponse;
import com.alisha.customerservice.dto.RegisterRequest;
import com.alisha.customerservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String token = authService.login(request);

        return new LoginResponse(token);
    }

}