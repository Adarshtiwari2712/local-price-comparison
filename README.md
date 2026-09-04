# Local Price Comparison

A Spring Boot backend application that allows users to compare product prices across different local stores.

## 🚀 Project Overview

The Local Price Comparison application is designed to help users find and compare the prices of products available at different local shops.

The backend provides REST APIs for managing:

- Products
- Local stores
- Product prices
- Price comparison
- Exception handling
- Input validation

## 🛠️ Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- REST API
- Maven
- Postman

## 🏗️ Project Architecture

The project follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL Database

### Project Structure

```text
com.example.local
├── controller
├── service
├── repository
├── model
├── dto
└── exception
```

## ✨ Current Features

### Product Management

- Add a product
- Get all products
- Search product by name
- Prevent duplicate products
- Validate product name

### Local Store Management

- Add local store
- Get all stores
- Validate store name and address
- Validate 10-digit phone number

### Price Management

- Add or update product price for a store
- Get all prices
- Compare prices between stores
- Find the cheapest store

### Exception Handling

Centralized exception handling is implemented for:

- Product not found
- Store not found
- Duplicate product
- No prices available
- Invalid request data

## 🔗 API Endpoints

### Products

```text
POST   /products
GET    /products
GET    /products/search?name=Milk
```

### Stores

```text
POST   /stores
GET    /stores
```

### Prices

```text
POST   /prices
GET    /prices
GET    /prices/compare/name?name=Milk
```

## 🗄️ Database

The application uses MySQL with Spring Data JPA and Hibernate.

Database name:

```text
local_price_compare
```

The database password is not stored directly in the repository.

The application reads it from the environment variable:

```properties
spring.datasource.password=${DB_PASSWORD}
```

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone https://github.com/Adarshtiwari2712/local-price-comparison.git
```

### 2. Create the MySQL database

```sql
CREATE DATABASE local_price_compare;
```

### 3. Configure the database password

Set the environment variable:

```text
DB_PASSWORD
```

### 4. Run the application

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8082
```

## 📌 Example

For a product such as **Milk**, different local stores can have different prices:

```text
Ravi Store     ₹45
Gupta Store    ₹50
Aman Store     ₹55
```

The comparison API identifies the cheapest available option.

## 🔮 Future Improvements

- Request DTO architecture improvements
- User and shopkeeper authentication
- Spring Security
- JWT authentication
- Swagger/OpenAPI documentation
- Unit and integration testing
- Frontend integration
- Docker deployment
- AI-powered price insights

## 👨‍💻 Author

**Adarsh Tiwari**

GitHub:

https://github.com/Adarshtiwari2712