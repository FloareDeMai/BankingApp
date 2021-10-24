package com.florentina.bankingapplication.exception.domain;

public class AmountNegativeException extends Exception{
    public AmountNegativeException(String message){
        super(message);
    }
}
