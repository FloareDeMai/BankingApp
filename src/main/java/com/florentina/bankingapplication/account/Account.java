package com.florentina.bankingapplication.account;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.florentina.bankingapplication.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String accountNumber;
    private BigDecimal currentBalance;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions;
}
