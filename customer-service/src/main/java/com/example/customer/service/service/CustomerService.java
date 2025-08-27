package com.example.customer.service.service;

import com.example.common.dto.CustomerRequest;
import com.example.common.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

        List<CustomerResponse> getAllCustomers();
        CustomerResponse findCustomerById(Long id);
        CustomerResponse createCustomer(CustomerRequest req);
        CustomerResponse updateCustomer(Long id, CustomerRequest req);
        void deleteCustomer(Long id);


}
