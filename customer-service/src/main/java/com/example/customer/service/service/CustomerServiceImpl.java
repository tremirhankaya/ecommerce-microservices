package com.example.customer.service.service;

import com.example.customer.service.entity.Customer;
import com.example.customer.service.repository.CustomerRepository;
import jakarta.transaction.Transactional;
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
    public List<Customer> getAllcustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + customer.getEmail());
        }
        Customer savedCustomer = customerRepository.save(customer);
        System.out.println("Customer created with ID: " + savedCustomer.getId());
        return customerRepository.save(savedCustomer);

    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setStreet(updatedCustomer.getStreet());
        existing.setCity(updatedCustomer.getCity());
        existing.setState(updatedCustomer.getState());
        existing.setPostalCode(updatedCustomer.getPostalCode());
        existing.setCountry(updatedCustomer.getCountry());
        System.out.println("Customer updated with ID: " + id);
       return customerRepository.save(existing);

    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
        System.out.println("Customer deleted with ID: " + id);
    }
}
