package com.tacoloco.order_total.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.tacoloco.order_total.model.ErrorResponse;

/**
 * Custom exception handler
 * @author John Wilde
 * @version 1.0.1
 */
@ControllerAdvice(annotations = RestController.class)
public class ApplicationExceptionHandler {
    
    // Handles errors related to input validation
    @ExceptionHandler(InputErrorException.class)
    public final ResponseEntity<ErrorResponse> handleValidationException(InputErrorException ex) {
        List<String> details = new ArrayList<>();
        details.addAll(ex.getErrors());
        ErrorResponse error = new ErrorResponse("Bad Request - Invalid Input", details, 400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handles errors related to input validation
    @ExceptionHandler(DatabaseException.class)
    public final ResponseEntity<ErrorResponse> handleDatabaseException(DatabaseException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getError());
        ErrorResponse error = new ErrorResponse("Database Error", details, 400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
