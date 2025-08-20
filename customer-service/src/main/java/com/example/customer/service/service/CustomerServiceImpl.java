package com.example.customer.service.service;

import com.example.customer.service.entity.Customer;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public List<Customer> getAllcustomers() {
        return List.of();
    }

    @Override
    public Customer findCustomerById(Long id) {
        return null;
    }

    @Override
    public void createCustomer(Customer customer) {

    }

    @Override
    public void updateCustomer(Long id, Customer updatedCustomer) {

    }

    @Override
    public void deleteCustomer(Long id) {

    }
}
