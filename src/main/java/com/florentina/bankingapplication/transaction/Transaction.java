package com.florentina.bankingapplication.transaction;

import com.florentina.bankingapplication.account.Account;
import com.florentina.bankingapplication.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
