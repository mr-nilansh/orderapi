package com.fooddelivery.orderapi.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    // Constructors
    public MenuItem() {}

    public MenuItem(String name, double price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }

    // Getters & Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", restaurant=" + (restaurant != null ? restaurant.getId() : null) +
                '}';
    }
}
