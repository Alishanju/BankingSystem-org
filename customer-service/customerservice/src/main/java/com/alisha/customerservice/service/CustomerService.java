package com.alisha.customerservice.service;

import com.alisha.customerservice.dto.CustomerRequest;
import com.alisha.customerservice.dto.CustomerResponse;
import com.alisha.customerservice.entity.Customer;
import com.alisha.customerservice.exception.CustomerNotFoundException;
import com.alisha.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    private CustomerResponse map(Customer customer) {

        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .username(customer.getUsername())
                .role(customer.getRole())
                .build();
    }

    public List<CustomerResponse> getAllCustomers() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public CustomerResponse getCustomerById(Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found"));

        return map(customer);
    }

    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found"));

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        Customer savedCustomer = repository.save(customer);

        return map(savedCustomer);
    }

    public void deleteCustomer(Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found"));

        repository.delete(customer);
    }
}