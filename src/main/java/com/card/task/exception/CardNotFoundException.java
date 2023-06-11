package com.card.task.exception;

import com.card.shared.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class CardNotFoundException extends BaseException {

    public CardNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, asProblemDetail("Card Not Found", message, HttpStatus.NOT_FOUND), null);
    }

    public CardNotFoundException(Long cardId) {
        super(HttpStatus.NOT_FOUND, asProblemDetail("Card Not Found", String.format("No Card Found With ID %d", cardId), HttpStatus.NOT_FOUND), null);
    }
}
