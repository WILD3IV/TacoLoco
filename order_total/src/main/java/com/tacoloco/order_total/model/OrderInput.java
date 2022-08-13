package com.tacoloco.order_total.model;

import java.util.ArrayList;

import lombok.Data;

/**
 * OrderInput class represents the root of the JSON input body.
 * 
 * @author John Wilde
 * @version 1.0
 */
@Data
public class OrderInput {
    
    /**
     * @param orderItems for input body array of order items.
     */
    private ArrayList<OrderItem> orderItems;
    
}
