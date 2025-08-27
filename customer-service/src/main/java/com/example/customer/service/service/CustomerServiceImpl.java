package com.example.customer.service.service;

import com.example.common.dto.CustomerRequest;
import com.example.common.dto.CustomerResponse;
import com.example.customer.service.entity.Customer;
import com.example.customer.service.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    // Constructor Injection
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();    }

    @Override
    public CustomerResponse findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return mapToResponse(customer);
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest req) {
        if (customerRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + req.getEmail());
        }

        Customer customer = mapToEntity(new Customer(), req);
        Customer savedCustomer = customerRepository.save(customer);

        System.out.println("Customer created with ID: " + savedCustomer.getId());
        return mapToResponse(savedCustomer);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest req) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        Customer updatedCustomer = mapToEntity(existing, req);
        Customer saved = customerRepository.save(updatedCustomer);

        System.out.println("Customer updated with ID: " + id);
        return mapToResponse(saved);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
        System.out.println("Customer deleted with ID: " + id);
    }

    // Request → Entity
    private Customer mapToEntity(Customer customer, CustomerRequest req) {
        customer.setFirstName(req.getFirstName());
        customer.setLastName(req.getLastName());
        customer.setEmail(req.getEmail());
        customer.setPhone(req.getPhone());
        customer.setStreet(req.getStreet());
        customer.setCity(req.getCity());
        customer.setState(req.getState());
        customer.setPostalCode(req.getPostalCode());
        customer.setCountry(req.getCountry());
        return customer;
    }

    // Entity → Response
    private CustomerResponse mapToResponse(Customer c) {
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
