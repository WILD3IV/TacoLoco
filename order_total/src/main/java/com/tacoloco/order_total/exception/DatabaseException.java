package com.tacoloco.order_total.exception;
import lombok.Getter;
import lombok.Setter;

/**
 * Custom runtime exception for db retrieval error
 * @author John Wilde
 * @version 1.0
 */

public class DatabaseException extends RuntimeException{

    @Getter @Setter
    private String error;
    public DatabaseException(String error) {
        this.error = error;
    }
    
}
