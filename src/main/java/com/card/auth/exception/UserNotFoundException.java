
package com.card.auth.exception;

import com.card.shared.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;

/**
 *
 * @author simiyu
 */
public class UserNotFoundException extends ResourceNotFound {

    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, asProblemDetail("User Not Found", message), null);
    }
    
}
