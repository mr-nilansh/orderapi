package com.fooddelivery.orderapi;

import com.fooddelivery.orderapi.model.Customer;
import com.fooddelivery.orderapi.model.MenuItem;
import com.fooddelivery.orderapi.model.Restaurant;
import com.fooddelivery.orderapi.repository.CustomerRepository;
import com.fooddelivery.orderapi.repository.MenuItemRepository;
import com.fooddelivery.orderapi.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderapiApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(
            CustomerRepository customerRepo,
            RestaurantRepository restaurantRepo,
            MenuItemRepository menuRepo
    ) {
        return args -> {
            // Only add if the DB is empty
            if (customerRepo.count() == 0 && restaurantRepo.count() == 0) {
                // Create Customer
                Customer customer = new Customer();
                customer.setName("John Doe");
                customer.setEmail("john@example.com");
                customer = customerRepo.save(customer);  // ID will be 1

                // Create Restaurant
                Restaurant restaurant = new Restaurant();
                restaurant.setName("Pizza Palace");
                restaurant = restaurantRepo.save(restaurant); // ID will be 1

                // Create Menu Items
                MenuItem item1 = new MenuItem();
                item1.setName("Margherita Pizza");
                item1.setPrice(8.99);
                item1.setRestaurant(restaurant);
                menuRepo.save(item1);  // ID will be 1

                MenuItem item2 = new MenuItem();
                item2.setName("Veggie Pizza");
                item2.setPrice(9.99);
                item2.setRestaurant(restaurant);
                menuRepo.save(item2);  // ID will be 2

                System.out.println("Sample customer, restaurant, and menu items added.");
            }
        };
    }
}