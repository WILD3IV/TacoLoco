package com.tacoloco.order_total.exception;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Custom runtime exception for db retrieval error
 * @author John Wilde
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DatabaseException extends RuntimeException{

    private String error;
    public DatabaseException(String error) {
        this.error = error;
    }
    
}
