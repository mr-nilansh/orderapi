package com.fooddelivery.orderapi.repository;

import com.fooddelivery.orderapi.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
