package com.example.customer.service.controller;

import com.example.common.dto.CustomerRequest;
import com.example.common.dto.CustomerResponse;
import com.example.customer.service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/list")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/list/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@RequestBody @Valid CustomerRequest req) {
        return customerService.createCustomer(req);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse update(@PathVariable Long id,
                                   @RequestBody @Valid CustomerRequest req) {
        return customerService.updateCustomer(id, req);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//response body yok
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}