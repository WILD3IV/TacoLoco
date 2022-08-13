package com.tacoloco.order_total.exception;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Custom runtime exception for input error validation
 * @author John Wilde
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class InputErrorException extends RuntimeException {

    private List<String> errors;
    public InputErrorException(List<String> errors) {
        this.errors = errors;
    }
    
}
