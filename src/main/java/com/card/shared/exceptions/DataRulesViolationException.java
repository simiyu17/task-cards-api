package com.card.shared.exceptions;

public class DataRulesViolationException extends RuntimeException {

    public DataRulesViolationException(String message){
        super(message);
    }
}
