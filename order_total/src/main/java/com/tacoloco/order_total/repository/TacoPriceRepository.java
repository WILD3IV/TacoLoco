package com.tacoloco.order_total.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tacoloco.order_total.model.MenuPrice;
/**
 * Handles CRUD operations for MenuPrice 
 * @author John Wilde
 * @version 1.0
 */
@Repository
public interface TacoPriceRepository extends JpaRepository<MenuPrice, Integer>{

    Optional<MenuPrice> findByItemType(String itemType);
}
