package com.florentina.bankingapplication.transaction;
import com.florentina.bankingapplication.account.Account;
import com.florentina.bankingapplication.exception.domain.AccountNotFoundException;
import com.florentina.bankingapplication.exception.domain.AmountNegativeException;
import com.florentina.bankingapplication.exception.domain.MinimumAmountException;
import com.florentina.bankingapplication.payload.response.TransferResponse;
import java.math.BigDecimal;
import java.util.List;



public interface TransactionService {

    List<TransactionDto> getAllTransactionsByAccountNumber(String accountNumber) throws AccountNotFoundException;

    Account deposit(String numberAccount, BigDecimal amount) throws AccountNotFoundException, AmountNegativeException, MinimumAmountException;

    Account withdrawal(String numberAccount, BigDecimal amount) throws AccountNotFoundException, AmountNegativeException, MinimumAmountException;

    TransferResponse transferBetweenAccounts(String fromAccountNumber, String toAccountNumber, BigDecimal amount) throws AccountNotFoundException, AmountNegativeException, MinimumAmountException;
}
