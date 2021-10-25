package com.florentina.bankingapplication.transaction;


import com.florentina.bankingapplication.account.Account;
import com.florentina.bankingapplication.exception.domain.AccountNotFoundException;
import com.florentina.bankingapplication.exception.domain.AmountNegativeException;
import com.florentina.bankingapplication.exception.domain.MinimumAmountException;
import com.florentina.bankingapplication.payload.request.TransactionRequest;
import com.florentina.bankingapplication.payload.response.TransferResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

  private final TransactionService transactionService;

    @GetMapping("/all/{accountNumber}")
    List<TransactionDto> getAllTransactionsByAccountNumber(@PathVariable String accountNumber) throws AccountNotFoundException {
        return transactionService.getAllTransactionsByAccountNumber(accountNumber);
    }


    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestBody TransactionRequest transactionRequest) throws AccountNotFoundException, AmountNegativeException, MinimumAmountException {
        Account account = transactionService.deposit(transactionRequest.getAccountNumber(), transactionRequest.getAmount());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @PostMapping("/withdrawal")
    public ResponseEntity<Account> withdrawal(@RequestBody TransactionRequest transactionRequest) throws AccountNotFoundException, AmountNegativeException {
        Account account = transactionService.withdrawal(transactionRequest.getAccountNumber(), transactionRequest.getAmount());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/transfer/{fromAccount}/{toAccount}/{amount}")
    public TransferResponse transfer(@PathVariable String fromAccount, @PathVariable String toAccount, @PathVariable BigDecimal amount) throws AccountNotFoundException, AmountNegativeException, MinimumAmountException {
      return transactionService.transferBetweenAccounts(fromAccount, toAccount, amount);

    }


}
