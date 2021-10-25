package com.florentina.bankingapplication.account;


import com.florentina.bankingapplication.exception.domain.AccountExistException;
import com.florentina.bankingapplication.exception.domain.AccountNotFoundException;
import com.florentina.bankingapplication.exception.domain.AmountNegativeException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;


    @PostMapping("/new-account")
    public ResponseEntity<Account> openNewAccount(@RequestBody NewAccountDto account) throws AmountNegativeException, AccountExistException {
        Account accountSaved = accountService.saveNewAccount(account);
        return new ResponseEntity<>(accountSaved, HttpStatus.OK);
    }


    @GetMapping("/all")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/by-balance-greater-than/{amount}")
    public List<Account> getAllAccountsBuBalanceGreaterThan(@PathVariable(name = "amount") BigDecimal amount) {
        return accountRepository.getAccountsByCurrentBalanceGreaterThan(amount);
    }

    @GetMapping("/by-balance-less-than/{amount}")
    public List<Account> getAllAccountsBuBalanceLessThan(@PathVariable(name = "amount") BigDecimal amount) {
        return accountRepository.getAccountsByCurrentBalanceLessThan(amount);
    }

    @GetMapping("/balance/{accountNumber}")
    public BigDecimal getBalanceByAccountNumber(@PathVariable(name = "accountNumber") String accountNumber) throws AccountNotFoundException {
        return accountService.getBalanceByAccountNumber(accountNumber);
    }
}
