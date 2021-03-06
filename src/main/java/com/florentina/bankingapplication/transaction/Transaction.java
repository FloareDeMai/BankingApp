package com.florentina.bankingapplication.transaction;

import com.florentina.bankingapplication.account.Account;
import com.florentina.bankingapplication.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_number")
    private Account account;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
