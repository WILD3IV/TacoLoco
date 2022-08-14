package com.tacoloco.order_total.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * MenuPrice class used for retrieving prices from DB
 * 
 * @author John Wilde
 * @version 1.0
 */

@Entity
@Table(name="MenuPrice")
@Data
public class MenuPrice {

    @Id
    private Integer idItem;

    private String itemType;

    private BigDecimal itemPrice;

    
}
