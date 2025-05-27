package com.fooddelivery.orderapi.repository;

import com.fooddelivery.orderapi.model.Customer;
import com.fooddelivery.orderapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByRestaurantId(Long restaurantId);
    @Query("SELECT CASE WHEN COUNT(oi) > 0 THEN true ELSE false END " +
            "FROM Order o JOIN o.orderItems oi " +
            "WHERE oi.menuItem.id = :menuItemId")
    boolean existsOrderItemsByMenuItemId(@Param("menuItemId") Long menuItemId);
    List<Order> findByCustomer(Customer customer);
}