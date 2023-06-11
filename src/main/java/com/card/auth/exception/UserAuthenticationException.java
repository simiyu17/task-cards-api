package com.card.auth.exception;

import com.card.shared.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class UserAuthenticationException extends BaseException {

    public UserAuthenticationException(String message) {
        super(HttpStatus.UNAUTHORIZED, asProblemDetail("User unauthenticated", message, HttpStatus.UNAUTHORIZED), null);
    }
}
