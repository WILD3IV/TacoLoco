package com.tacoloco.order_total.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * OrderTotalResponse class represents the return JSON.
 * 
 * @author John Wilde
 * @version 1.0
 */
@Data
public class OrderTotalResponse {

    /**@param orderTotal - total price of all items */
    private BigDecimal orderPriceTotal = new BigDecimal(0);

    private BigDecimal hotDogPriceTotal = new BigDecimal(0);
    
    private BigDecimal tacoPriceTotal = new BigDecimal(0);
    
    private BigDecimal tacoQty = new BigDecimal(0);

    /**@param orderTotal - total price of all items */
    private BigDecimal orderItemQtyTotal = new BigDecimal(0);

    /**@param discountApplied - percent discount applied to order */
    private BigDecimal discountApplied = new BigDecimal(0);

    /**@param total - order total after discounts applied */
    private BigDecimal grandTotal = new BigDecimal(0);
}
