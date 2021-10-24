package com.florentina.bankingapplication.exception.domain;

public class AccountExistException extends Exception{
    public AccountExistException(String message){
        super(message);
    }
}
