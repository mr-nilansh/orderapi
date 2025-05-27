package com.fooddelivery.orderapi.controller;

import com.fooddelivery.orderapi.model.MenuItem;
import com.fooddelivery.orderapi.model.Restaurant;
import com.fooddelivery.orderapi.repository.MenuItemRepository;
import com.fooddelivery.orderapi.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PutMapping("/menu-items/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem updatedItem) {
        return menuItemRepository.findById(id)
                .map(menuItem -> {
                    menuItem.setName(updatedItem.getName());
                    menuItem.setPrice(updatedItem.getPrice());
                    return menuItemRepository.save(menuItem);
                })
                .orElseThrow(() -> new RuntimeException("MenuItem not found: " + id));
    }

    @DeleteMapping("/menu-items/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("MenuItem not found: " + id);
        }
        menuItemRepository.deleteById(id);
    }
}
