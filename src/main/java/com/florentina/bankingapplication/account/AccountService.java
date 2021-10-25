package com.florentina.bankingapplication.account;


import com.florentina.bankingapplication.exception.domain.AccountExistException;
import com.florentina.bankingapplication.exception.domain.AccountNotFoundException;
import com.florentina.bankingapplication.exception.domain.AmountNegativeException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;



public interface AccountService {

Account saveNewAccount(NewAccountDto newAccountDto) throws AmountNegativeException;
BigDecimal getBalanceByAccountNumber(String accountNumber) throws AccountNotFoundException;
}
