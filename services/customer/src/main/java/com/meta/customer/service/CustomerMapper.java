package com.meta.customer.service;

import com.meta.customer.customer.Customer;
import com.meta.customer.dto.CustomerRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {


    public Customer toCustomer(CustomerRequest customerRequest) {
        Customer customer=Customer.builder()
                .id(customerRequest.id())
                .email(customerRequest.email())
                .address(customerRequest.address())
                .firstname(customerRequest.firstname())
                .lastname(customerRequest.lastname())
                .build();
        return customer;
    }
}
