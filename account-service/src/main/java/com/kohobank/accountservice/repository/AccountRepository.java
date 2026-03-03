package com.kohobank.accountservice.repository;

import com.kohobank.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findAccountByCustomerId(Long customerId);

    Optional<Account> findAccountByAccountNumber(Long accountNumber);

    /**
     * SELECT *
     * FROM accounts
     * WHERE customer_id IN (101, 103);
     * @param customerIds
     * @return
     */
    List<Account> findAllByCustomerIdIn(List<Long> customerIds);
}
