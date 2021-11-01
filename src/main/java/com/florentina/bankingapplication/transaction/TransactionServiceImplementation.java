package com.florentina.bankingapplication.transaction;

import com.florentina.bankingapplication.account.Account;
import com.florentina.bankingapplication.account.AccountRepository;
import com.florentina.bankingapplication.enums.TransactionType;
import com.florentina.bankingapplication.exception.domain.AccountNotFoundException;
import com.florentina.bankingapplication.exception.domain.AmountNegativeException;
import com.florentina.bankingapplication.exception.domain.MinimumAmountException;
import com.florentina.bankingapplication.payload.response.TransferResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;


    public List<TransactionDto> getAllTransactionsByAccountNumber(String accountNumber) throws AccountNotFoundException {
        Account account = accountRepository.getAccountByAccountNumber(accountNumber);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        if (account == null) {
            throw new AccountNotFoundException("There is no account found with this number: " + accountNumber);
        }
        Optional<List<Transaction>> optionalTransactionList = transactionRepository.findByAccount(account);
        optionalTransactionList.ifPresent(transactions -> transactions.forEach(transaction -> transactionDtoList.add(covertTransactionToTransactionDto(transaction))));

        return transactionDtoList;
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Account deposit(String numberAccount, BigDecimal amount, TransactionType transactionType) throws AccountNotFoundException, AmountNegativeException, MinimumAmountException {
        Account account = accountRepository.getAccountByAccountNumber(numberAccount);

        if (account == null) {
            throw new AccountNotFoundException("There is no account found with this number: " + numberAccount);
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new AmountNegativeException("Amount must be positive!");
        }

        if (amount.compareTo(new BigDecimal(2)) < 0) {
            throw new MinimumAmountException("Deposit should be greater than 2 or equal");
        }
        Transaction transaction = new Transaction();
        transaction.setBalanceBefore(account.getCurrentBalance());

        account.setCurrentBalance((account.getCurrentBalance()).add(amount));

        transaction.setAmount(amount);
        transaction.setBalanceAfter(account.getCurrentBalance());
        transaction.setAccount(account);
        transaction.setTransactionType(transactionType);
        transactionRepository.save(transaction);
        return account;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Account withdrawal(String numberAccount, BigDecimal amount, TransactionType transactionType) throws AccountNotFoundException, AmountNegativeException, MinimumAmountException {
        Account account = accountRepository.getAccountByAccountNumber(numberAccount);

        if (account == null) {
            throw new AccountNotFoundException("There is no account found with this number: " + numberAccount);
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new AmountNegativeException("Amount must be positive!");
        }

        if (amount.compareTo(new BigDecimal(2)) < 0) {
            throw new MinimumAmountException("Amount must be greater than 2 or equal");
        }


        BigDecimal currentBalance = account.getCurrentBalance();

        if (!(currentBalance.compareTo(amount) >= 0)) {
            throw new AmountNegativeException("Insufficient funds! Your current balance is: " + account.getCurrentBalance());
        }
        Transaction transaction = new Transaction();
        transaction.setBalanceBefore(account.getCurrentBalance());
        account.setCurrentBalance((account.getCurrentBalance()).subtract(amount));

        transaction.setAmount(amount);
        transaction.setBalanceAfter(account.getCurrentBalance());
        transaction.setAccount(account);
        transaction.setTransactionType(transactionType);
        transactionRepository.save(transaction);
        return account;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public TransferResponse transferBetweenAccounts(String fromAccountNumber, String toAccountNumber, BigDecimal amount) throws AccountNotFoundException, AmountNegativeException, MinimumAmountException {
        withdrawal(fromAccountNumber, amount, TransactionType.AMOUNT_SENT);
        deposit(toAccountNumber, amount, TransactionType.AMOUNT_RECEIVED);
        return TransferResponse.builder()
                .amount(amount)
                .fromAccount(fromAccountNumber)
                .toAccount(toAccountNumber)
                .build();
    }

    private TransactionDto covertTransactionToTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .accountNumber(transaction.getAccount().getAccountNumber())
                .amount(transaction.getAmount())
                .balanceBefore(transaction.getBalanceBefore())
                .balanceAfter(transaction.getBalanceAfter())
                .transactionType(transaction.getTransactionType())
                .build();
    }
}
