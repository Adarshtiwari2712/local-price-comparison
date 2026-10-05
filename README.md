# Local Price Compare

A full-stack web application that helps users compare product prices from nearby local shops.

Shopkeepers can register their stores, manage products and prices, update product availability, and maintain their store information. Users can search for products and compare prices from shops located within a 5 km radius.

---

## 📌 Project Overview

Local Price Compare is designed to solve a simple real-world problem:

> **Users often have to visit multiple local shops or bargain with shopkeepers to find a better price.**

This application allows users to search for a product and view:

- Nearby shops selling the product
- Product price
- Product availability
- Distance from the user's current location
- Shop address
- Shop phone number
- Cheapest available price

Shopkeepers can manage their own store, products, and prices through a secure dashboard.

The project focuses on providing a simple and practical solution for local price comparison while demonstrating real-world full-stack development concepts.

---

# ✨ Features

## 👤 User Features

- Search for products
- Find nearby shops within a 5 km radius
- Compare prices between shops
- View the cheapest available price
- View product availability
- View shop distance
- View shop address
- View shop phone number
- Location-based product search
- Search without requiring user login
- Display nearby shops sorted by distance

---

## 🏪 Shopkeeper Features

- Shopkeeper registration
- Store creation during registration
- Store location registration
- Secure login
- JWT-based authentication
- BCrypt password hashing
- Shopkeeper dashboard
- View store information
- Update store information
- Add products
- Update products
- Delete products
- Add product prices
- Update prices
- Delete prices
- Update product availability
- View products belonging to their own store
- View prices belonging to their own store

---

## 🔐 Security Features

- JWT authentication
- BCrypt password hashing
- Role-based authorization
- Shopkeeper-only management APIs
- Store ownership validation
- Product ownership validation
- Price ownership validation
- Protected CRUD operations
- CORS configuration
- Request validation
- Global exception handling
- Authentication and authorization error handling

<<---- SECURITY CONSIDERATION ---->>

The application uses:

BCrypt password hashing
JWT authentication
Spring Security
Role-based authorization
Store ownership checks
Product ownership checks
Price ownership checks
Protected shopkeeper APIs

---

# 📍 Location-Based Search

The application uses the browser's **Geolocation API** to obtain the user's current latitude and longitude.

The user's location is sent to the Spring Boot backend.

The backend calculates the distance between the user and each shop using the **Haversine formula**.

Only shops within a maximum distance of **5 km** are returned.

Example:

```text
User
 │
 │ Current Location
 ▼
Backend
 │
 ├── Shop A → 1.2 km
 ├── Shop B → 3.4 km
 ├── Shop C → 4.8 km
 └── Shop D → 8.2 km ❌
                  │
                  └── Outside 5 km radius



🏗️ Application Architecture

The project follows a layered architecture.

                    ┌─────────────────────┐
                    │      Frontend       │
                    │   HTML/CSS/JavaScript│
                    └──────────┬──────────┘
                               │
                               │ REST API
                               ▼
                    ┌─────────────────────┐
                    │    Controllers      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Services       │
                    │    Business Logic   │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Repositories     │
                    │    Spring Data JPA  │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │       MySQL         │
                    │      Database       │
                    └─────────────────────┘



<<<---🛠️ Technology Stack--->>>

---Backend----
  
Java 21
Spring Boot
Spring Security
JWT
Spring Data JPA
Hibernate
MySQL
Maven
Jakarta Bean Validation
Swagger / OpenAPI


---Frontend---

HTML5
CSS3
JavaScript
Fetch API
Browser Geolocation API
Local Storage

---Development Tools---

IntelliJ IDEA
MySQL Workbench
Postman
Swagger UI
Git
GitHub



<<<<---- 📁 BACKEND PROJECT STRUCTURE ---->>>>

src/main/java/com/example/local
│
├── LocalApplication.java
│
├── config
│   ├── SecurityConfig.java
│   ├── JwtAuthenticationFilter.java
│   └── OpenAPIConfig.java
│
├── controller
│   ├── AuthController.java
│   ├── ProductController.java
│   ├── PriceController.java
│   └── LocalStoreController.java
│
├── service
│   ├── AuthService.java
│   ├── ProductService.java
│   ├── PriceService.java
│   └── LocalStoreService.java
│
├── repository
│   ├── UserRepository.java
│   ├── ProductRepository.java
│   ├── LocalStoreRepository.java
│   └── PriceRepository.java
│
├── model
│   ├── User.java
│   ├── Product.java
│   ├── LocalStore.java
│   └── Price.java
│
├── dto
│   ├── LoginRequest.java
│   ├── AuthResponseDTO.java
│   ├── ProductRequestDTO.java
│   ├── ProductResponseDTO.java
│   ├── PriceRequestDTO.java
│   ├── PriceResponseDTO.java
│   ├── PriceComparisonDTO.java
│   ├── ShopkeeperRegistrationRequest.java
│   ├── MyStoreProductResponseDTO.java
│   ├── UpdatePriceRequestDTO.java
│   ├── UpdateProductRequestDTO.java
│   └── UpdateStoreRequestDTO.java
│
└── exception
    ├── GlobalExceptionHandler.java
    ├── EmailAlreadyExistsException.java
    ├── ProductAlreadyExistsException.java
    ├── ProductNotFoundException.java
    ├── ProductHasPricesException.java
    ├── PriceNotFoundException.java
    ├── StoreNotFoundException.java
    ├── StoreAlreadyExistsException.java
    ├── StoreHasPricesException.java
    └── NoPricesAvailableException.java


<<<<---- 🌐 FRONTEND PROJECT STRUCTURE ---->>>>

local-frontend/
│
├── index.html
│
├── css/
│   ├── style.css
│   ├── welcome.css
│   ├── role.css
│   ├── user.css
│   ├── auth.css
│   └── dashboard.css
│
├── js/
│   ├── app.js
│   ├── navigation.js
│   ├── api.js
│   ├── auth.js
│   ├── user.js
│   └── dashboard.js
│
└── pages/
    ├── role.html
    │
    ├── user/
    │   └── user.html
    │
    └── shopkeeper/
        ├── register.html
        ├── login.html
        └── dashboard.html


<<<---🗄️ DATABASE DESIGN--->>>
The application uses MySQL with Spring Data JPA and Hibernate.

User
 │
 │ owns
 ▼
LocalStore
 │
 │ contains
 ▼
Product
 │
 │ has
 ▼
Price




<<<--- 👤 USER --->>>

The User entity stores shopkeeper authentication information.

Important fields include:

id
name
email
password
role

Passwords are never stored as plain text. They are hashed using BCrypt.

<<<---🏪 LOCAL STORE --->>>

The LocalStore entity stores shop information.

Important fields include:

id
name
address
phone
latitude
longitude
owner_id

A shopkeeper is associated with their store through the owner relationship.

The store's latitude and longitude are used for nearby-shop searching.


<<<<---- 🔄 APPLICATION FLOW ---->>>>

User Flow

Open Website
     │
     ▼
Choose Role
     │
     ▼
User
     │
     ▼
Enter Product
     │
     ▼
Allow Location
     │
     ▼
Nearby Price Search
     │
     ▼
Compare Shops
     │
     ├── Price
     ├── Distance
     ├── Availability
     ├── Address
     └── Phone

Shopkeeper Flow

Open Website
     │
     ▼
Choose Role
     │
     ▼
Shopkeeper
     │
     ├──────────────┐
     ▼              ▼
 Register          Login
     │              │
     ▼              ▼
 Create Store    JWT Authentication
     │              │
     └──────┬───────┘
            ▼
       Dashboard
            │
            ├── Manage Store
            ├── Add Product
            ├── Update Product
            ├── Delete Product
            ├── Add Price
            ├── Update Price
            └── Delete Price


<<<<---- 📍 NEARBY SEARCH ALGORITHM ---->>>>

The nearby search works in the following steps:
1. User enters a product name.

2. Browser requests the user's location.

3. Frontend obtains:
       Latitude
       Longitude

4. Frontend sends the location to the backend.

5. Backend finds shops selling the requested product.

6. Backend calculates the distance between
   the user and each shop.

7. Shops farther than 5 km are removed.

8. Remaining shops are sorted by distance.

9. Cheapest available price is calculated.

10. Results are returned to the frontend.

This approach keeps the important distance filtering logic on the backend.


<<<<---- 🧮 DISTANCE CALCULATION ---->>>>

The application uses the Haversine formula to calculate the approximate distance between two geographical coordinates.
 
the calculation uses--
User Latitude
User Longitude
Store Latitude
Store Longitude



<<<<---- 📱 CURRENT PROJECT SCOPE ---->>>>

The current version focuses on the core functionality required for a practical local price comparison platform.

The project intentionally avoids unnecessary complexity and focuses on:

Simple user experience
Secure shopkeeper management
Product and price management
Location-based comparison
Nearby shop discovery
Maintainable backend architecture

<<<<---- 🔮 FUTURE IMPROVEMENTS ---->>>>

The following features can be added in future versions.

Version 2
Price history
Price trend visualization
Favorite products
Favorite shops
Multi-product comparison
Shopping list
Total shopping cost
Estimated savings
Improved search suggestions
Advanced filtering
Better sorting options
Version 3

AI-based features can be added later, such as:

AI price recommendations
Product alternatives
Personalized shopping suggestions
Natural-language product search
AI-based deal recommendations
Price trend analysis

AI features are planned future improvements and are not part of the current implemented version.

🎯 Project Goals

The main goals of Local Price Compare are:

1.Help users find better prices from nearby local shops.
2.Reduce unnecessary shop-to-shop searching.
3.Help local shopkeepers manage their products digitally.
4.Provide location-based price comparison.
5.Demonstrate practical full-stack development using Java and Spring Boot.
6.Build a project that can be extended with advanced features in the future.



.

<<<<---- 🎓 PROJECT PURPOSE ---->>>>

This project was developed as a practical full-stack application to gain experience with:

Java backend development
Spring Boot
REST APIs
Database management
Authentication and authorization
Frontend-backend integration
Location-based services
Real-world CRUD operations
Software project architecture



<<<<----👨‍💻 AUTHOR ---->>>>

Adarsh Tiwari

B.Tech – Computer Science and Engineering

Areas of Interest
Java Backend Development
Spring Boot
REST APIs
Database Systems
Full-Stack Development
AI-integrated Applications


<<<<<<----📄 LICENSE ---->>>>>>

This project is developed for educational, learning, portfolio, and internship purposes.

The source code may be used, modified, and extended for personal learning and development. If you use or build upon this project, proper credit to the original author is appreciated.

**Author:** Adarsh Tiwari 


## 📸SCREENSHOTS ##

### Role Selection

![Role Selection](screenshots/role-selection.png)

### Shopkeeper Dashboard

![Shopkeeper Dashboard](screenshots/shopkeeper-dashboard.png)

### User Price Comparison

![Price Comparison](screenshots/price-comparison.png)