package com.fooddelivery.orderapi.controller;

import com.fooddelivery.orderapi.model.MenuItem;
import com.fooddelivery.orderapi.model.Restaurant;
import com.fooddelivery.orderapi.repository.MenuItemRepository;
import com.fooddelivery.orderapi.repository.OrderRepository;
import com.fooddelivery.orderapi.service.MenuItemService;
import com.fooddelivery.orderapi.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final RestaurantService restaurantService;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;

    public MenuItemController(MenuItemService menuItemService,
                              RestaurantService restaurantService,
                              MenuItemRepository menuItemRepository, OrderRepository orderRepository) {
        this.menuItemService = menuItemService;
        this.restaurantService = restaurantService;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        return ResponseEntity.ok(menuItemService.createMenuItem(menuItem));
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItem>> getItemsByRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return ResponseEntity.ok(menuItemService.getMenuItemsByRestaurant(restaurant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem updatedMenuItem) {
        return menuItemRepository.findById(id)
                .map(menuItem -> {
                    menuItem.setName(updatedMenuItem.getName());
                    menuItem.setPrice(updatedMenuItem.getPrice());
                    MenuItem savedItem = menuItemRepository.save(menuItem);
                    return ResponseEntity.ok(savedItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long id) {
        if (orderRepository.existsOrderItemsByMenuItemId(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Cannot delete menu item because it is associated with existing orders.");
        }
        return menuItemRepository.findById(id)
                .map(menuItem -> {
                    menuItemRepository.delete(menuItem);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

//    @PutMapping("/menu-items/{id}")
//    public ResponseEntity<MenuItem> updateMenuItem(
//            @PathVariable Long id,
//            @RequestBody MenuItem updatedItem) {
//
//        return menuItemRepository.findById(id)
//                .map(item -> {
//                    item.setName(updatedItem.getName());
//                    item.setPrice(updatedItem.getPrice());
//                    return ResponseEntity.ok(menuItemRepository.save(item));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//
//    @DeleteMapping("/menu-items/{id}")
//    public ResponseEntity<Object> deleteMenuItem(@PathVariable Long id) {
//        return menuItemRepository.findById(id)
//                .map(item -> {
//                    menuItemRepository.delete(item);
//                    return ResponseEntity.noContent().build();
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }


    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<MenuItem> createMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuItem menuItem) {

        // 1. Find the restaurant by ID
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // 2. Set the restaurant into the menu item
        menuItem.setRestaurant(restaurant);

        // 3. Save the menu item
        MenuItem savedItem = menuItemService.createMenuItem(menuItem);

        // 4. Return saved item
        return ResponseEntity.ok(savedItem);
    }
}