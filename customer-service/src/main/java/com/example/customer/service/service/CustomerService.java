package com.example.customer.service.service;

import com.example.customer.service.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllcustomers();
    Customer findCustomerById(Long id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id,Customer updatedCustomer);
    void deleteCustomer(Long id);
}
