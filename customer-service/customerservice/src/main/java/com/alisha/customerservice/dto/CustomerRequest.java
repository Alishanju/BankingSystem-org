package com.alisha.customerservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    private String phone;
}