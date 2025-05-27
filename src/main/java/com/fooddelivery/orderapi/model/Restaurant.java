package com.fooddelivery.orderapi.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    // Constructors
    public Restaurant() {}

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getters & Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}