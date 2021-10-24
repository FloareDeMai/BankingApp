package com.florentina.bankingapplication.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsAccountByAccountNumber(String accountNumber);
    List<Account> getAccountsByCurrentBalanceGreaterThan(BigDecimal amount);
    List<Account> getAccountsByCurrentBalanceLessThan(BigDecimal amount);
    Account getAccountByAccountNumber(String accountNumber);

}
