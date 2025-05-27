package com.fooddelivery.orderapi.service;

import com.fooddelivery.orderapi.model.MenuItem;
import com.fooddelivery.orderapi.model.Restaurant;
import com.fooddelivery.orderapi.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItem> getMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    public List<MenuItem> getMenuItemsByRestaurant(Restaurant restaurant) {
        return menuItemRepository.findByRestaurant(restaurant);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    public MenuItem updateMenuItem(Long id, MenuItem updatedItem) {
        return menuItemRepository.findById(id)
                .map(item -> {
                    item.setName(updatedItem.getName());
                    item.setPrice(updatedItem.getPrice());
                    item.setRestaurant(updatedItem.getRestaurant());
                    return menuItemRepository.save(item);
                })
                .orElseThrow(() -> new RuntimeException("MenuItem not found"));
    }

    public List<MenuItem> getMenuItemsByIds(List<Long> ids) {
        return menuItemRepository.findAllById(ids);
    }
}