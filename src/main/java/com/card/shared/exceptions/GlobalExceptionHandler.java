package com.card.shared.exceptions;

import com.card.auth.exception.UserAuthenticationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TIME_STAMP = "timestamp";


    @ExceptionHandler({ UsernameNotFoundException.class })
    public ErrorResponse handleAuthenticationUserNotFoundException(UsernameNotFoundException ex) {
        return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage())
                .title("User Not Found")
                .property(TIME_STAMP, Instant.now())
                .build();
    }

    @ExceptionHandler({ AuthenticationException.class, UserAuthenticationException.class})
    public ErrorResponse handleAuthenticationException(AuthenticationException ex) {
        return ErrorResponse.builder(ex, HttpStatus.UNAUTHORIZED, ex.getMessage())
                .title("Not Authenticated")
                .property(TIME_STAMP, Instant.now())
                .build();
    }

    @ExceptionHandler(value = { BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse unAuthenticatedException(BadCredentialsException ex) {
        return ErrorResponse.builder(ex, HttpStatus.UNAUTHORIZED, ex.getMessage())
                .title("Not Authenticated")
                .property(TIME_STAMP, Instant.now())
                .build();
    }

    @ExceptionHandler(value = { AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        return ErrorResponse.builder(ex, HttpStatus.FORBIDDEN, ex.getMessage())
                .title("Card Found, but you do not have enough permissions")
                .property(TIME_STAMP, Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse>
    handleValidationExceptions(ConstraintViolationException ex) throws JsonProcessingException {

        var details = new HashMap<String, String>();
        for (var error : ex.getConstraintViolations()) {
            details.put(String.valueOf(error.getPropertyPath()), error.getMessage());
        }
        ErrorResponse error = ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, "Constraint Violations")
                .title("Bad Request")
                .property("violations", new ObjectMapper().writeValueAsString(details))
                .property(TIME_STAMP, Instant.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = { PropertyReferenceException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePropertyReferenceException(PropertyReferenceException ex) {
        return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage())
                .title("Unknown Property")
                .property(TIME_STAMP, Instant.now())
                .build();
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse unCaughtRuntimeException(RuntimeException ex){
        ex.printStackTrace();
        return ErrorResponse.builder(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage())
                .title("Error Occurred")
                .property(TIME_STAMP, Instant.now())
                .build();
    }


}
