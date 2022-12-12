package com.example.fintechapi.service;

import com.example.fintechapi.exceptions.CustomerNotFound;
import com.example.fintechapi.model.customer.Customer;
import com.example.fintechapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new CustomerNotFound();
                });
    }

    public Customer findCustomerByFullName(String fullName) {
        return customerRepository
                .findCustomerByFullName(fullName)
                .orElseThrow(() -> {
                    throw new CustomerNotFound();
                });
    }

    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository
                .findCustomerByPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    throw new CustomerNotFound();
                });
    }

    public Customer findCustomerByEmailAddress(String emailAddress) {
        return customerRepository
                .findCustomerByEmailAddress(emailAddress)
                .orElseThrow(() -> {
                    throw new CustomerNotFound();
                });
    }

}
