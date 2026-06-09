package com.alisha.customerservice.service;

import com.alisha.customerservice.dto.CustomerRequest;
import com.alisha.customerservice.entity.Customer;
import com.alisha.customerservice.exception.CustomerNotFoundException;
import com.alisha.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

        @Mock
        private CustomerRepository repository;

        @InjectMocks
        private CustomerService service;

        @Test
        void shouldReturnCustomerById() {

                Customer customer = Customer.builder()
                                .id(1L)
                                .name("Aasif")
                                .email("aasif@gmail.com")
                                .phone("9999999999")
                                .username("aasif")
                                .role("USER")
                                .build();

                when(repository.findById(1L))
                                .thenReturn(Optional.of(customer));

                var response = service.getCustomerById(1L);

                assertEquals("Aasif",
                                response.getName());

                verify(repository)
                                .findById(1L);
        }

        @Test
        void shouldThrowExceptionWhenCustomerNotFound() {

                when(repository.findById(1L))
                                .thenReturn(Optional.empty());

                assertThrows(
                                CustomerNotFoundException.class,
                                () -> service.getCustomerById(1L));

                verify(repository)
                                .findById(1L);
        }
}