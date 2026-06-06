package com.alisha.customerservice.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String username;

    private String role;
}