
package com.card.auth.exception;

import com.card.shared.exceptions.BaseException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author simiyu
 */
public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, asProblemDetail("User Not Found", message, HttpStatus.NOT_FOUND), null);
    }
    
}
