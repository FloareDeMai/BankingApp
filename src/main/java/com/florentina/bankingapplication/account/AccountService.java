package com.florentina.bankingapplication.account;


import com.florentina.bankingapplication.exception.domain.AccountExistException;
import com.florentina.bankingapplication.exception.domain.AccountNotFoundException;
import com.florentina.bankingapplication.exception.domain.AmountNegativeException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    public Account saveNewAccount(Account formAccount) throws AmountNegativeException, AccountExistException {
        // check if another account exists by account number
        if(accountRepository.existsAccountByAccountNumber(formAccount.getAccountNumber())){
            throw new AccountExistException("This account number is already taken!");
        }

        Account account = new Account();
        account.setAccountNumber(formAccount.getAccountNumber());

        // check balance
        if(formAccount.getCurrentBalance()==null){
            account.setCurrentBalance(new BigDecimal(0));
        }else if ((formAccount.getCurrentBalance()).compareTo(BigDecimal.ZERO)< 0){
            throw new AmountNegativeException("Balance must be positive");
        }else if((formAccount.getCurrentBalance()).compareTo(BigDecimal.ZERO) >= 0){
            account.setCurrentBalance(formAccount.getCurrentBalance());
        }

        accountRepository.save(account);
        return account;
    }

    @Transactional
    public BigDecimal getBalanceByAccountNumber(String accountNumber) throws AccountNotFoundException {
        Account account = accountRepository.getAccountByAccountNumber(accountNumber);
        if(account == null){
            throw new AccountNotFoundException("There is no account with the given account number!");
        }

        return account.getCurrentBalance();
    }

}
