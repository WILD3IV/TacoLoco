package com.tacoloco.order_total.exception;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom runtime exception for input error validation
 * @author John Wilde
 * @version 1.0
 */
public class InputErrorException extends RuntimeException {

    @Getter @Setter
    private List<String> errors;
    public InputErrorException(List<String> errors) {
        this.errors = errors;
    }
    
}
