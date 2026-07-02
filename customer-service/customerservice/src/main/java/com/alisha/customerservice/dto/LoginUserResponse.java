package com.alisha.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponse {

    private Long id;

    private String username;

    private String email;

    private String role;

}