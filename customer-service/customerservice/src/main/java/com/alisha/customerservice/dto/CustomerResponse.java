package com.alisha.customerservice.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String username;
    private String role;
}