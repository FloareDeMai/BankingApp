package com.florentina.bankingapplication.exception.domain;

public class AccountNotFoundException extends Exception{
    public AccountNotFoundException(String message){
        super(message);
    }
}
