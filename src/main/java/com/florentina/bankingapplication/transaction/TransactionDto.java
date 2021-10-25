package com.florentina.bankingapplication.transaction;


import com.florentina.bankingapplication.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDto {
    private String accountNumber;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private BigDecimal balanceBefore;
    private TransactionType transactionType;

}
