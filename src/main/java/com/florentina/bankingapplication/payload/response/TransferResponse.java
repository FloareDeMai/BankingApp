package com.florentina.bankingapplication.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransferResponse {
    private BigDecimal amount;
    private String fromAccount;
    private String toAccount;
}
