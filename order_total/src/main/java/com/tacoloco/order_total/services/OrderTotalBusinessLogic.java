package com.tacoloco.order_total.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class OrderTotalBusinessLogic {

    // Establish logging...
    private static final Logger log = LogManager.getLogger(OrderTotalBusinessLogic.class);

    @Autowired
    TacoPriceRepository tacoPriceRepo;

    public void getOrderTotal (OrderInput orderInput, OrderTotalResponse orderTotalResponse) {
        for(OrderItem orderItem : orderInput.getOrderItems()) {

            MenuPrice menuPrice = tacoPriceRepo.findByItemType(orderItem.getItemType())
                .orElseThrow(() -> new DatabaseException("Price could not be retrieved from database"));

            switch (orderItem.getMenuType()) {
                case "Taco" :
                    orderTotalResponse.setTacoPriceTotal(orderTotalResponse.getTacoPriceTotal().add(menuPrice.getItemPrice().multiply(orderItem.getItemQty())));
                    orderTotalResponse.setTacoQty(orderTotalResponse.getTacoQty().add(orderItem.getItemQty()));
                case "Hot Dog":
                    orderTotalResponse.setHotDogPriceTotal(orderTotalResponse.getHotDogPriceTotal().add(menuPrice.getItemPrice().multiply(orderItem.getItemQty())));
                default:break;
            }

        }

        orderTotalResponse.setOrderPriceTotal(orderTotalResponse.getTacoPriceTotal().add(orderTotalResponse.getHotDogPriceTotal())); // set order total before discount where total = tacos + hotdogs

        if(orderTotalResponse.getTacoQty().compareTo(new BigDecimal(4)) >= 0) { // if total taco qty >= 4 apply auto 20% discount
            orderTotalResponse.setDiscountApplied(new BigDecimal(0.20)); // set applied discount for the return
            orderTotalResponse.setTacoPriceTotal(applyDiscount(orderTotalResponse.getTacoPriceTotal(), new BigDecimal(0.2)));
        } 

        orderTotalResponse.setGrandTotal(orderTotalResponse.getTacoPriceTotal().add(orderTotalResponse.getHotDogPriceTotal())); // add them again once discounts applied
        
         // round BigDecimal for return formatting
         orderTotalResponse.setOrderPriceTotal(orderTotalResponse.getOrderPriceTotal().setScale(2, RoundingMode.HALF_UP));
         orderTotalResponse.setGrandTotal(orderTotalResponse.getGrandTotal().setScale(2, RoundingMode.HALF_UP));
         orderTotalResponse.setDiscountApplied(orderTotalResponse.getDiscountApplied().setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * sets grand total after applying discount
     * @param orderTotalResponse
     * @param discount
     * @return total with applied discount
     */
    private BigDecimal applyDiscount(BigDecimal total, BigDecimal discount) {

        return total.subtract(total.multiply(discount));
    }
}
