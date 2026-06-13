package com.alisha.customerservice.service;

import com.alisha.customerservice.dto.CustomerRequest;
import com.alisha.customerservice.dto.CustomerResponse;
import com.alisha.customerservice.entity.Customer;
import com.alisha.customerservice.exception.CustomerNotFoundException;
import com.alisha.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
        log.info("Fetching all customers");
        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Cacheable(value = "customers", key = "#id")
    public CustomerResponse getCustomerById(Long id) {
        System.out.println("get from DB");
        log.info("Fetching customer with id {}", id);
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found"));

        return map(customer);
    }

    @CachePut(value = "customers", key = "#id")
    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request) {
        log.info("Updating customer with id {}", id);
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found"));

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        Customer savedCustomer = repository.save(customer);
        log.info("Customer updated successfully with id {}", id);

        return map(savedCustomer);
    }

    @CacheEvict(value = "customers", key = "#id")
    public void deleteCustomer(Long id) {
        log.info("Deleting customer with id {}", id);

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found"));

        repository.delete(customer);
        log.info("Customer deleted successfully with id {}", id);
    }
}