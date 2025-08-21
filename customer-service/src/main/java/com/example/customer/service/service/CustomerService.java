package com.example.customer.service.service;

import com.example.customer.service.dto.CustomerRequest;
import com.example.customer.service.dto.CustomerResponse;
import com.example.customer.service.entity.Customer;

import java.util.List;

public interface CustomerService {

        List<CustomerResponse> getAllCustomers();
        CustomerResponse findCustomerById(Long id);
        CustomerResponse createCustomer(CustomerRequest req);
        CustomerResponse updateCustomer(Long id, CustomerRequest req);
        void deleteCustomer(Long id);


}
