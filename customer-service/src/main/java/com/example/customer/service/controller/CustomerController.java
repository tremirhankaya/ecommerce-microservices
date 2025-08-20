package com.example.customer.service.controller;

import com.example.customer.service.dto.CustomerRequest;
import com.example.customer.service.dto.CustomerResponse;
import com.example.customer.service.entity.Customer;
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
    final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllcustomers().stream().map(this::toResponse).toList();


    }
    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        Customer customer = customerService.findCustomerById(id);
        return toResponse(customer);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@RequestBody @Valid CustomerRequest req) {
        Customer  customer = new Customer();
        customer.setFirstName(req.getFirstName());
        customer.setLastName(req.getLastName());
        customer.setEmail(req.getEmail());
        customer.setPhone(req.getPhone());
        customer.setStreet(req.getStreet());
        customer.setCity(req.getCity());
        customer.setState(req.getState());
        customer.setPostalCode(req.getPostalCode());
        customer.setCountry(req.getCountry());

        Customer saved= customerService.createCustomer(customer);
        return toResponse(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse update(@PathVariable Long id, @RequestBody @Valid CustomerRequest req) {
        Customer customer = new Customer();

        customer.setFirstName(req.getFirstName());
        customer.setLastName(req.getLastName());
        customer.setEmail(req.getEmail());
        customer.setPhone(req.getPhone());
        customer.setStreet(req.getStreet());
        customer.setCity(req.getCity());
        customer.setState(req.getState());
        customer.setPostalCode(req.getPostalCode());
        customer.setCountry(req.getCountry());

        Customer saved= customerService.updateCustomer(id,customer);
        return toResponse(saved);



    }
    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getPhone(),
                c.getStreet(),
                c.getCity(),
                c.getState(),
                c.getPostalCode(),
                c.getCountry()
        );
    }

}
