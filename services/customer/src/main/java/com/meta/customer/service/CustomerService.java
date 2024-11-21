package com.meta.customer.service;

import com.meta.customer.customer.Customer;
import com.meta.customer.dto.CustomerRequest;
import com.meta.customer.dto.CustomerResponse;
import com.meta.customer.exception.CustomerNotFindException;
import com.meta.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

  @Autowired
    private CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest customerRequest) {
        if (customerRequest == null) {
            return "Customer eklenirken hata oldu";
        }
        Customer customer = customerMapper.toCustomer(customerRequest);
        customerRepository.insert(customer);

        return "Customer başarıyla eklendi: " + customer.getId();
    }

    public void updateCustomer(CustomerRequest customerRequest) {
        var customerInstance = customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFindException(
                        String.format("Cannot update custpmer:: No Customer found with the provided ID::%s", customerRequest.id())
                ));
        mergerCustomer(customerInstance, customerRequest);
        customerRepository.save(customerInstance);

    }

    private void mergerCustomer(Customer customerInstance, CustomerRequest customerRequest) {
        if (!Objects.equals(customerRequest.firstname(), "")) {
            customerInstance.setFirstname(customerRequest.firstname());
        }

        if (!Objects.equals(customerRequest.lastname(), "")) {
            customerInstance.setLastname(customerRequest.lastname());
        }

        if (!Objects.equals(customerRequest.email(), "")) {
            customerInstance.setEmail(customerRequest.email());
        }
        if (customerRequest.address() != null) {
            customerInstance.setAddress(customerRequest.address());
        }
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(customer -> new CustomerResponse(customer.getId(), customer.getFirstname(), customer.getLastname(), customer.getEmail(), customer.getAddress()))
                .toList();

    }

    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getFirstname(),
                        customer.getLastname(),
                        customer.getEmail(),
                        customer.getAddress()
                ))
                .orElseThrow(() -> new CustomerNotFindException(
                        String.format("Cannot find customer with ID: %s", customerId)
                ));
    }

    public String deleteById(String customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFindException(
                        String.format("Cannot find customer with ID: %s", customerId)
                ));
        customerRepository.deleteById(customerId);

        return "Customer deleted successfully!";
    }

}
