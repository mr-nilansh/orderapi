package com.fooddelivery.orderapi.controller;

import com.fooddelivery.orderapi.model.Order;
import com.fooddelivery.orderapi.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create order for a customer at a restaurant, with menuItemId -> quantity map
    @PostMapping("/customer/{customerId}/restaurant/{restaurantId}")
    public ResponseEntity<Order> createOrderForCustomer(@PathVariable Long customerId, @PathVariable Long restaurantId, @RequestBody Map<Long, Integer> menuItemQuantities) {
        Order createdOrder = orderService.createOrderForCustomer(customerId, restaurantId, menuItemQuantities);
        return ResponseEntity.ok(createdOrder);
    }

    // Get all orders for a customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    // Get all orders for a restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        List<Order> orders = orderService.getOrdersByRestaurant(restaurantId);
        return ResponseEntity.ok(orders);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Update order status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestBody Map<String, String> statusUpdate) {
        String newStatus = statusUpdate.get("orderStatus");
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }

    // Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}