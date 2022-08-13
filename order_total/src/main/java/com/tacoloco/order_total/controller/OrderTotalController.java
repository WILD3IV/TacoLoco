package com.tacoloco.order_total.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tacoloco.order_total.model.OrderInput;
import com.tacoloco.order_total.model.OrderTotalResponse;
import com.tacoloco.order_total.services.OrderInputValidation;
import com.tacoloco.order_total.services.OrderTotalBusinessLogic;

/**
 * REST Controller - handles REST requests
 * 
 * @author John Wilde
 * @version 1.0
 */
@RestController
public class OrderTotalController {

    @Autowired
    OrderTotalBusinessLogic orderTotalBL; // autowire BL object for class

    @Autowired
    OrderInputValidation orderInputValidation;

    private static final Logger log = LogManager.getLogger(OrderTotalController.class);

    @PostMapping("/orderTotal")
    OrderTotalResponse getOrderTotal(@RequestBody OrderInput orderInput) {
        log.info("Getting order total from /getOrderTotal");
        
        orderInputValidation.validate(orderInput); // validate input

        OrderTotalResponse orderTotalResponse = new OrderTotalResponse(); // Create response object
        
        orderTotalBL.getOrderTotal(orderInput, orderTotalResponse); // call order calcualator method from BL

        return orderTotalResponse;        
    }
    
}
