package com.card.task.exception;

import com.card.shared.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;

public class CardNotFoundException extends ResourceNotFound {

    public CardNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, asProblemDetail("Card Not Found", message), null);
    }
}
