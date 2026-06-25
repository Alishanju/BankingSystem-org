package com.alisha.customerservice.controller;

import com.alisha.customerservice.dto.CustomerRequest;
import com.alisha.customerservice.dto.CustomerResponse;
import com.alisha.customerservice.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

        private final CustomerService customerService;

        @GetMapping
        public List<CustomerResponse> getAllCustomers() {

                return customerService.getAllCustomers();
        }

        @GetMapping("/{id}")
        public CustomerResponse getCustomerById(
                        @PathVariable Long id, HttpServletRequest request) {
                log.info(
                                "traceparent={}",
                                request.getHeader("traceparent"));

                log.info(
                                "X-B3-TraceId={}",
                                request.getHeader("X-B3-TraceId"));
                // long start = System.currentTimeMillis();

                // CustomerResponse response = customerService.getCustomerById(id);

                // log.info("Customer API took {} ms",
                // System.currentTimeMillis() - start);

                // return response;
                return customerService.getCustomerById(id);
        }

        @PutMapping("/{id}")
        public CustomerResponse updateCustomer(
                        @PathVariable Long id,
                        @Valid @RequestBody CustomerRequest request) {

                return customerService.updateCustomer(id, request);
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public String deleteCustomer(
                        @PathVariable Long id) {

                customerService.deleteCustomer(id);

                return "Customer deleted successfully";
        }
}