package com.florentina.bankingapplication.account;


import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NewAccountDto {
    private BigDecimal currentBalance;
}
