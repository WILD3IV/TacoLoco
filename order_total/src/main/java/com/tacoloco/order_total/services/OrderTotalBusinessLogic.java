package com.tacoloco.order_total.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tacoloco.order_total.model.OrderInput;
import com.tacoloco.order_total.model.OrderItem;
import com.tacoloco.order_total.model.OrderTotalResponse;

/**
 * OrderTotalBusinessLogic contains business logic for totaling orders.
 * 
 * @author John Wilde
 * @version 1.0
 */
@Component
public class OrderTotalBusinessLogic {

    // Establish logging...
    private static final Logger log = LogManager.getLogger(OrderTotalBusinessLogic.class);

    final BigDecimal veggieTacoPrice = new BigDecimal(2.5);
    final BigDecimal chickenTacoPrice = new BigDecimal(3);
    final BigDecimal beefTacoPrice = new BigDecimal(3);
    final BigDecimal chorizoTacoPrice = new BigDecimal(3.5);

    /**
     * @param orderInput - Input object
     * @param orderTotalResponse - Response object
     */

    public void getOrderTotal (OrderInput orderInput, OrderTotalResponse orderTotalResponse) {
        
        log.info("OrderTotalBusinessLogic.getOrderTotal");
       
        
        for(OrderItem orderItem : orderInput.getOrderItems()) {
            switch (orderItem.getItemType()) { // item type switch for dealing with distinct item types
                case "Veggie Taco" : 
                    if (orderItem.getItemPrice() == null) { 
                        log.info("Price not provided - setting default");
                        orderItem.setItemPrice(veggieTacoPrice); 
                    }
                case "Chicken Taco" :
                    if (orderItem.getItemPrice() == null) { 
                        log.info("Price not provided - setting default");
                        orderItem.setItemPrice(chickenTacoPrice); 
                    }
                case "Beef Taco" :
                    if (orderItem.getItemPrice() == null) { 
                        log.info("Price not provided - setting default");
                        orderItem.setItemPrice(beefTacoPrice); 
                    }
                case "Chorizo Taco" :
                    if (orderItem.getItemPrice() == null) { 
                        log.info("Price not provided - setting default");
                        orderItem.setItemPrice(chorizoTacoPrice); 
                    }
                default: break;
            }


            orderTotalResponse.setOrderPriceTotal(orderTotalResponse.getOrderPriceTotal().add(orderItem.getItemPrice().multiply(orderItem.getItemQty()))); // order total = order total + (item price * item qty)
            orderTotalResponse.setOrderItemQtyTotal(orderTotalResponse.getOrderItemQtyTotal().add(orderItem.getItemQty())); // update total order item quantity
            log.info("Qty: {}", orderTotalResponse.getOrderItemQtyTotal());
            log.info("Total: {}", orderTotalResponse.getOrderPriceTotal());
        }

        if(orderTotalResponse.getOrderItemQtyTotal().compareTo(new BigDecimal(4)) >= 0) { // if total item qty >= 4 apply auto 20% discount
            applyDiscount(orderTotalResponse, new BigDecimal(0.2));
        } else { // else set the grand total without any discount
            orderTotalResponse.setGrandTotal(orderTotalResponse.getOrderPriceTotal()); // set grand total
            orderTotalResponse.setDiscountApplied(new BigDecimal(0)); // set discount applied
        }

        // round BigDecimal for return formatting
        orderTotalResponse.setOrderPriceTotal(orderTotalResponse.getOrderPriceTotal().setScale(2, RoundingMode.HALF_UP));
        orderTotalResponse.setGrandTotal(orderTotalResponse.getGrandTotal().setScale(2, RoundingMode.HALF_UP));
        orderTotalResponse.setDiscountApplied(orderTotalResponse.getDiscountApplied().setScale(2, RoundingMode.HALF_UP));
    }   

    /**
     * sets grand total after applying discount
     * @param orderTotalResponse
     * @param discount
     */
    private void applyDiscount(OrderTotalResponse orderTotalResponse, BigDecimal discount) {

        BigDecimal orderWithDiscount = orderTotalResponse.getOrderPriceTotal().subtract(orderTotalResponse.getOrderPriceTotal().multiply(discount)); // determine order total with discount: grandTotal = total - (total * discount)

        orderTotalResponse.setGrandTotal(orderWithDiscount); // set grand total
        orderTotalResponse.setDiscountApplied(discount); // set discount applied
    }
}
