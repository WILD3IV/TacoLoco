package com.tacoloco.order_total.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * OrderItem class represents individual items in the orderItem input array.
 * 
 * @author John Wilde
 * @version 1.0
 */
@Data
public class OrderItem {
    
    private String itemType;

    private BigDecimal itemQty;

    @JsonIgnore
    private BigDecimal itemPrice;
    
}
