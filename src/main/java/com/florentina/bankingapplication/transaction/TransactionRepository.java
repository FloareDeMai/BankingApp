package com.florentina.bankingapplication.transaction;


import com.florentina.bankingapplication.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> getTransactionsByAccount(Account account);
    Optional<List<Transaction>> findByAccount(Account account);
}
