package com.kohobank.accountservice.repository;

import com.kohobank.accountservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findCustomerByMobileNumber(String mobileNumber);

    @Modifying
    @Transactional
    void deleteCustomerByCustomerId(Long customerId);

}
