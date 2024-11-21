package com.meta.customer.controller;


import com.meta.customer.dto.CustomerRequest;
import com.meta.customer.dto.CustomerResponse;
import com.meta.customer.exception.CustomerNotFindException;
import com.meta.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")

public class CustomerController {

   @Autowired
    private  CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest customerRequest)
    {
      return new ResponseEntity<>(customerService.createCustomer(customerRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer( @RequestBody @Valid CustomerRequest customerRequest)  {
        customerService.updateCustomer(customerRequest);

        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        return  new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
    }

    @GetMapping("/exists/{customerId}")
    public ResponseEntity<Boolean> existsById(@PathVariable String customerId){
        return ResponseEntity.ok(customerService.existsById(customerId));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable String customerId){
        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteById(@PathVariable String customerId){
        return ResponseEntity.ok(customerService.deleteById(customerId));
    }
}
