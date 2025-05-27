package com.fooddelivery.orderapi.service;

import com.fooddelivery.orderapi.model.Customer;
import com.fooddelivery.orderapi.model.MenuItem;
import com.fooddelivery.orderapi.model.Order;
import com.fooddelivery.orderapi.model.OrderItem;
import com.fooddelivery.orderapi.model.Restaurant;
import com.fooddelivery.orderapi.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final CustomerService customerService;

    public OrderService(OrderRepository orderRepository,
                        RestaurantService restaurantService,
                        MenuItemService menuItemService,
                        CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
        this.customerService = customerService;
    }

    /**
     * Create an Order for a Restaurant only (no customer linked).
     * @param restaurantId ID of the restaurant.
     * @param menuItemQuantities Map of menuItemId -> quantity.
     * @return saved Order entity.
     */
    @Transactional
    public Order createOrder(Long restaurantId, Map<Long, Integer> menuItemQuantities) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        for (Map.Entry<Long, Integer> entry : menuItemQuantities.entrySet()) {
            Long menuItemId = entry.getKey();
            Integer quantity = entry.getValue();

            MenuItem menuItem = menuItemService.getMenuItemById(menuItemId)
                    .orElseThrow(() -> new RuntimeException("MenuItem not found: " + menuItemId));

            if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
                throw new RuntimeException("MenuItem " + menuItemId + " does not belong to Restaurant " + restaurantId);
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(quantity);
            orderItem.setOrder(null);  // Will link later

            orderItems.add(orderItem);

            totalPrice += menuItem.getPrice() * quantity;
        }

        Order order = new Order();
        order.setRestaurant(restaurant);
        order.setOrderStatus("Pending");
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        // Link back order in each orderItem
        orderItems.forEach(item -> item.setOrder(order));

        return orderRepository.save(order);
    }

    /**
     * Create an Order linked to a Customer and Restaurant.
     * @param customerId ID of the customer placing the order.
     * @param restaurantId ID of the restaurant.
     * @param menuItemQuantities Map of menuItemId -> quantity.
     * @return saved Order entity.
     */
    @Transactional
    public Order createOrderForCustomer(Long customerId, Long restaurantId, Map<Long, Integer> menuItemQuantities) {
        if (menuItemQuantities == null || menuItemQuantities.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one menu item.");
        }

        Customer customer = customerService.getCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId));

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        for (Map.Entry<Long, Integer> entry : menuItemQuantities.entrySet()) {
            Long menuItemId = entry.getKey();
            Integer quantity = entry.getValue();

            if (quantity == null || quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero for menu item ID: " + menuItemId);
            }

            MenuItem menuItem = menuItemService.getMenuItemById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with ID: " + menuItemId));

            if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
                throw new IllegalArgumentException("MenuItem " + menuItemId + " does not belong to Restaurant " + restaurantId);
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(quantity);
            orderItem.setOrder(null);

            orderItems.add(orderItem);

            totalPrice += menuItem.getPrice() * quantity;
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setOrderStatus("Pending");
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        orderItems.forEach(item -> item.setOrder(order));

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        order.setOrderStatus(newStatus);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }
}

