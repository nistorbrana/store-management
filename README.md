# 🛒 Store Management API
This is a Java Spring Boot application that provides a simple store-management API
The application allows managing products with features like adding products, getting products and changing product price
The application also allows simple functionalities on users, like getting and adding users.

---

## 🚀 Technologies used
- Java 17
- Spring Boot
- Spring Data JPA
- Lombok
- Maven
- JUnit 5 and Mockito (for testing)
- SLF4J (for logging)

## 📦 Features
- Add product
- Get all products
- Get product by id
- Change product price
- Add user
- Get all users
- Field validations on product
- Exception handling
- Logging


## 📑 Endpoints

| Method | Endpoint               | Description          |
|--------|------------------------|----------------------|
| GET    | `/products`            | Get all products     |
| GET    | `/products/{id}`       | Get product by ID    |
| POST   | `/products`            | Add a new product    |
| PATCH  | `/products/{id}/price` | Update product price |
| GET    | `/users`               | Get all users        |
| POST   | `/users`               | Add a new user       |

> Data are transferred via `DTOs` and validated using annotations.

## 🛠 Installations steps

#### 1. Clone the Repository
```bash
  git clone https://github.com/nistorbrana/store-management.git
```
#### 2. Navigate to the project Directory:

```bash
  cd store-management
```
#### 3. Prerequisites
Ensure Java and Maven are installed properly on your local machine.

#### 4. Start the application
```bash
    mvn clean install
    mvn spring-boot:run
```

## ✅ Tests

Unit tests were written using JUnit 5 and Mockito.

To run the tests:
```bash
    mvn test
```
