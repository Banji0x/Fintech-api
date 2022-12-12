package com.example.fintechapi.repository;

import com.example.fintechapi.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
//    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findCustomerByFullName(String fullName);

    Optional<Customer> findCustomerByPhoneNumber(String phoneNumber);

    Optional<Customer> findCustomerByEmailAddress(String emailAddress);
}
