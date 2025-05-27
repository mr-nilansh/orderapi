package com.fooddelivery.orderapi.repository;

import com.fooddelivery.orderapi.model.MenuItem;
import com.fooddelivery.orderapi.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurant(Restaurant restaurant);
}