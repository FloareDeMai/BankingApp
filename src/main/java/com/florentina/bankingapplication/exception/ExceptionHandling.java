package com.florentina.bankingapplication.exception;


import com.florentina.bankingapplication.exception.domain.AccountExistException;
import com.florentina.bankingapplication.exception.domain.AccountNotFoundException;
import com.florentina.bankingapplication.exception.domain.AmountNegativeException;
import com.florentina.bankingapplication.exception.domain.MinimumAmountException;
import com.florentina.bankingapplication.payload.response.HttpResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {


    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";

    @ExceptionHandler(AccountExistException.class)
    public ResponseEntity<HttpResponse> accountExistException(AccountExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AmountNegativeException.class)
    public ResponseEntity<HttpResponse> balanceNegativeException(AmountNegativeException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<HttpResponse> accountNotFoundException(AccountNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MinimumAmountException.class)
    public ResponseEntity<HttpResponse> minimumAmountException(MinimumAmountException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }


    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {

        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }


}
