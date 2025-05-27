# 🍔 Food Delivery Order Management API

A backend system for a food delivery platform built using **Spring Boot**. This API handles both customer-side and restaurant-side operations, enabling a complete ordering workflow including customer registration, restaurant management, menu creation, order placement, and more.

---

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **Hibernate**
- **Docker & Docker Compose**
- **Lombok**
- **Postman** (for testing)
- **Git & GitHub**
- *(Upcoming)* JWT Authentication & Role-Based Access Control

---

## 📦 Features Implemented

### ✅ Customers
- Register a new customer
- View customer details
- Place orders for menu items

### ✅ Restaurants
- Register a new restaurant
- Add menu items
- View orders placed by customers

### ✅ Menu Items
- Add menu items to restaurants
- Retrieve all items of a restaurant

### ✅ Orders
- Place an order by customer for specific menu items
- Retrieve all orders placed
- Retrieve all orders for a restaurant

### ✅ Data Initialization
- On startup, sample data is automatically inserted:
  - 1 sample customer
  - 1 sample restaurant
  - 2 sample menu items

---

## 🔐 Security
- Separate roles: **CUSTOMER** and **RESTAURANT**

---

## 🧪 API Testing

You can test APIs using Postman or curl after running the application.

Example endpoints:
- `POST /customers`
- `GET /restaurants/{id}/menu-items`
- `POST /orders`
- `GET /orders/restaurant/{restaurantId}`

_(Full API documentation with Swagger coming soon)_

---

## 🚀 How to Run the Application

This section provides instructions for running the Food Delivery Order Management API either using Docker or locally on your machine.

### ✅ Prerequisites
Before running the application, ensure that the following tools are installed on your machine:

- Java 17
- Maven
- Git
- MySQL (if not using Docker)
- Docker & Docker Compose (if using containerized setup)

### 🐳 Option 1: Run with Docker (Recommended)

1. Clone the Repository
git clone https://github.com/mr-nilansh/orderapi.git
cd orderapi

3. Start the Application
Use Docker Compose to build and run both the Spring Boot application and MySQL:
docker-compose up --build

5. Verify Setup
Once the containers are running:
  - API will be available at: http://localhost:8080
  - MySQL will run on: localhost:3306 (inside the container)

### 💻 Option 2: Run Locally (Without Docker)
1. Clone the Repository
git clone https://github.com/mr-nilansh/orderapi.git
cd orderapi

2. Create the Database in MySQL
Log into your MySQL client and create a database:
CREATE DATABASE food_order_db;

3. Configure Database Credentials
Open src/main/resources/application.properties and set your local MySQL credentials:
 - spring.datasource.url=jdbc:mysql://localhost:3306/fooddeliverydb
 - spring.datasource.username=YOUR_USERNAME
 - spring.datasource.password=YOUR_PASSWORD

4. Run the Application
- Use Maven to build and run the application:
./mvnw clean install
./mvnw spring-boot:run

5. Access the API
- Once started, the API will be accessible at:
- http://localhost:8080

---

## 📦 API Endpoints

GET    /api/customers/{id}              - Get customer details  
POST   /api/customers                   - Create a new customer  
GET    /api/restaurants                 - List all restaurants  
POST   /api/restaurants                 - Register a restaurant  
GET    /api/menu/{restaurantId}         - Get menu for a restaurant  
POST   /api/orders                      - Place a new order  
GET    /api/orders/customer/{customerId} - Get customer order history

--- 

## 🧪 Sample Data on Startup

When the application starts, the following sample data is added automatically:
1 Sample Customer
1 Sample Restaurant
2 Menu Items under the restaurant

This is useful for testing endpoints immediately after setup.

---

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.
Fork the project
Create your feature branch (git checkout -b feature/YourFeature)
Commit your changes (git commit -m 'Add some feature')
Push to the branch (git push origin feature/YourFeature)
Open a Pull Request

---

## 📄 License

This project is open-source and available under the MIT License.
