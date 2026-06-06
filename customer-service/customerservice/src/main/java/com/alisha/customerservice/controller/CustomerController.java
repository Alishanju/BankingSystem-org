package com.alisha.customerservice.controller;

import com.alisha.customerservice.dto.CustomerRequest;
import com.alisha.customerservice.dto.CustomerResponse;
import com.alisha.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

        private final CustomerService customerService;

        @GetMapping
        public List<CustomerResponse> getAllCustomers() {

                return customerService.getAllCustomers();
        }

        @GetMapping("/{id}")
        public CustomerResponse getCustomerById(
                        @PathVariable Long id) {

                return customerService.getCustomerById(id);
        }

        @PutMapping("/{id}")
        public CustomerResponse updateCustomer(
                        @PathVariable Long id,
                        @Valid @RequestBody CustomerRequest request) {

                return customerService.updateCustomer(id, request);
        }

        @DeleteMapping("/{id}")
        public String deleteCustomer(
                        @PathVariable Long id) {

                customerService.deleteCustomer(id);

                return "Customer deleted successfully";
        }
}