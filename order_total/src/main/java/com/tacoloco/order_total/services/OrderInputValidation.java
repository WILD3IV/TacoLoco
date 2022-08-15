package com.tacoloco.order_total.services;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tacoloco.order_total.exception.InputErrorException;
import com.tacoloco.order_total.model.OrderInput;
import com.tacoloco.order_total.model.OrderItem;

/**
 * Validation logic for input JSON
 * @author John Wilde
 * @version 1.0
 */
@Service
public class OrderInputValidation {

    // Establish logging...
    private static final Logger log = LogManager.getLogger(OrderInputValidation.class);
    
    public void validate(OrderInput orderInput) {
        ArrayList<String> inputErrors = new ArrayList<>();
        
        for(OrderItem orderItem : orderInput.getOrderItems()) {
            
            if("".equalsIgnoreCase(orderItem.getItemType()) || orderItem.getItemType() == null) {
                inputErrors.add("itemType can't be null or empty.");
            } else {
                if(!"Veggie Taco".equalsIgnoreCase(orderItem.getItemType()) && !"Beef Taco".equalsIgnoreCase(orderItem.getItemType()) &&
                        !"Chicken Taco".equalsIgnoreCase(orderItem.getItemType()) &&!"Chorizo Taco".equalsIgnoreCase(orderItem.getItemType()) && !"Hot Dog".equalsIgnoreCase(orderItem.getItemType()) ) {
                    inputErrors.add(orderItem.getItemType() + " is not on the menu");
                }
            }           

            if(orderItem.getItemQty() == null) { // validate qty is not null
                inputErrors.add("itemQty can't be null or empty.");
            }
        }

        if(inputErrors.size() > 0) {
            log.error("Errors Found in Input - see response body for details.");
            throw new InputErrorException(inputErrors);
        }
    }
}
