package com.example.customer.service.service;

import com.example.customer.service.dto.CustomerRequest;
import com.example.customer.service.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllcustomers();
    Customer findCustomerById(Long id);
    Customer createCustomer(CustomerRequest req);
    Customer updateCustomer(Long id,CustomerRequest req);
    void deleteCustomer(Long id);
}
