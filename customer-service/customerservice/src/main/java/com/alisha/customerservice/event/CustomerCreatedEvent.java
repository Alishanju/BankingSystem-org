package com.alisha.customerservice.event;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreatedEvent {

    private Long customerId;

    private String username;

    private String email;
}