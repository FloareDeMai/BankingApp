package com.florentina.bankingapplication.transaction;

import com.florentina.bankingapplication.account.Account;
import com.florentina.bankingapplication.account.AccountRepository;
import com.florentina.bankingapplication.enums.TransactionType;
import com.florentina.bankingapplication.exception.domain.AccountNotFoundException;
import com.florentina.bankingapplication.exception.domain.AmountNegativeException;
import com.florentina.bankingapplication.exception.domain.MinimumAmountException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;


    public List<Transaction> getAllTransactionsByAccountNumber(String accountNumber) throws AccountNotFoundException {
        Account account = accountRepository.getAccountByAccountNumber(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException("There is no account found with this number!");
        }

        return transactionRepository.getTransactionsByAccount(account);
    }


    public Account deposit(String numberAccount, BigDecimal amount) throws AccountNotFoundException, AmountNegativeException, MinimumAmountException {
        Account account = accountRepository.getAccountByAccountNumber(numberAccount);

        if (account == null) {
            throw new AccountNotFoundException("There is no account found with this number!");
        }

        if (amount.compareTo(new BigDecimal(0)) < 0) {
            throw new AmountNegativeException("Amount must be positive!");
        }

        if (amount.compareTo(new BigDecimal(2)) < 0) {
            throw new MinimumAmountException("Deposit should be greater than 2");
        }
        Transaction transaction = new Transaction();
        transaction.setBalanceBefore(account.getCurrentBalance());
        account.setCurrentBalance((account.getCurrentBalance()).add(amount));
        accountRepository.save(account);
        transaction.setAmount(amount);
        transaction.setBalanceAfter(account.getCurrentBalance());
        transaction.setAccount(account);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transactionRepository.save(transaction);
        return account;
    }

    public Account withdrawal(String numberAccount, BigDecimal amount) throws AccountNotFoundException, AmountNegativeException {
        Account account = accountRepository.getAccountByAccountNumber(numberAccount);
        if (account == null) {
            throw new AccountNotFoundException("There is no account found with this number!");
        }

        if (amount.compareTo(new BigDecimal(0)) < 0) {
            throw new AmountNegativeException("Amount must be positive!");
        }

        BigDecimal currentBalance = account.getCurrentBalance();

        if(!(currentBalance.compareTo(amount) >= 0)){
            throw new AmountNegativeException("Insufficient funds! Your current balance is: " + account.getCurrentBalance());
        }
        Transaction transaction = new Transaction();
        transaction.setBalanceBefore(account.getCurrentBalance());
        account.setCurrentBalance((account.getCurrentBalance()).subtract(amount));
        accountRepository.save(account);
        transaction.setAmount(amount);
        transaction.setBalanceAfter(account.getCurrentBalance());
        transaction.setAccount(account);
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transactionRepository.save(transaction);
        return account;
    }


}