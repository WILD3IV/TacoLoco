package com.tacoloco.order_total.model;

import java.util.List;
import lombok.Data;

/**
 * Represents JSON response object when error is thrown
 * @author John Wilde
 * @version 1.0
 */
@Data
public class ErrorResponse {
    
    public ErrorResponse(String error, List<String> message, Integer status) {
        super();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    private Integer status;
    private String error;
    private List<String>message;    
}
