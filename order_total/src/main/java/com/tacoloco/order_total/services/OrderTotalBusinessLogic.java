package com.tacoloco.order_total.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacoloco.order_total.exception.InputErrorException;
import com.tacoloco.order_total.exception.DatabaseException;
import com.tacoloco.order_total.model.MenuPrice;
import com.tacoloco.order_total.model.OrderInput;
import com.tacoloco.order_total.model.OrderItem;
import com.tacoloco.order_total.model.OrderTotalResponse;
import com.tacoloco.order_total.repository.TacoPriceRepository;

/**
 * OrderTotalBusinessLogic contains business logic for totaling orders.
 * Retrieves menu item prices from mySQL database
 * 
 * @author John Wilde
 * @version 1.1
 */
@Component
public class OrderTotalBusinessLogic {

    // Establish logging...
    private static final Logger log = LogManager.getLogger(OrderTotalBusinessLogic.class);

    @Autowired
    TacoPriceRepository tacoPriceRepo;

    final BigDecimal veggieTacoPrice = new BigDecimal(2.5); // Veggie Taco price default
    final BigDecimal chickenTacoPrice = new BigDecimal(3); // Chicken Taco price default
    final BigDecimal beefTacoPrice = new BigDecimal(3); // Beef Taco price default
    final BigDecimal chorizoTacoPrice = new BigDecimal(3.5); // Chorizo taco price default

    /**
     * @param orderInput - Input object
     * @param orderTotalResponse - Response object
     */
    public void getOrderTotal (OrderInput orderInput, OrderTotalResponse orderTotalResponse) {
        
        log.info("OrderTotalBusinessLogic.getOrderTotal");
        
        for(OrderItem orderItem : orderInput.getOrderItems()) {
            
            //get price from database
            MenuPrice menuPrice = tacoPriceRepo.findByItemType(orderItem.getItemType()).orElseThrow(() -> new DatabaseException("Price could not be retrieved from database"));
            log.info(menuPrice.toString());

            orderItem.setItemPrice(menuPrice.getItemPrice()); //set price
            log.info(orderItem.toString());

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
