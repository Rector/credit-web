package ru.kir.credit.web.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        CreditWebError creditWebError = new CreditWebError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(creditWebError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleInvalidDataException(InvalidDataException e) {
        CreditWebError creditWebError = new CreditWebError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(creditWebError, HttpStatus.BAD_REQUEST);
    }

}
