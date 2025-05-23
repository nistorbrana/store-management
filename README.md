# 🛒 Store Management API
This is a Java Spring Boot application that provides a simple store-management API.  
The application allows managing products with features like adding products, getting products and changing product price.  
The application also allows simple functionalities on users, like getting and adding users.

---

## 🚀 Technologies used
- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- Lombok
- Maven
- JUnit 5 and Mockito (for testing)
- SLF4J (for logging)

---

## 📦 Features
- Add product
- Get all products
- Get product by id
- Change product price
- Add user
- Get all users
- Field validations on product
- Basic authentication based on roles
- Exception handling
- Logging
---

## 📑 Endpoints

| Method | Endpoint               | Description          | Access         |
|--------|------------------------|----------------------|----------------|
| GET    | `/products`            | Get all products     | ADMIN, REGULAR |
| GET    | `/products/{id}`       | Get product by ID    | ADMIN, REGULAR |
| POST   | `/products`            | Add a new product    | ADMIN          |
| PATCH  | `/products/{id}/price` | Update product price | ADMIN          |
| GET    | `/users`               | Get all users        | ADMIN          |
| POST   | `/users`               | Add a new user       | ADMIN          |

> Data are transferred via `DTOs` and validated using annotations.

### 🛡️ Field Validations

#### 📦 Product Validations
| Field   | Validation                       |
|---------|----------------------------------|
| `name`  | must not be blank                |
| `price` | must not be null and must be > 0 |
| `stock` | must not be null and must be ≥ 0 |


#### 🙍🏻‍♂️ User Validations

| Field      | Validation                              |
|------------|-----------------------------------------|
| `username` | must not be blank and must be unique    |
| `password` | must not be blank                       |
| `role`     | must not be null (`ADMIN` or `REGULAR`) |

---
### 📬 Example Requests
#### ✅ Add Product (valid)

**Request:**
```http
POST /products
Content-Type: application/json
```
```json
{
  "name": "Phone",
  "price": 59.99,
  "stock": 5
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Phone",
  "price": 59.99,
  "stock": 5
}
```

#### ❌ Add Product (invalid)
**Request:**
```http
POST /products
Content-Type: application/json
```
```json
{
  "name" : "",
  "price" :  -20,
  "stock" : -2
}
```
**Response:**
```json
{
  "price": "Product price must be greater than 0",
  "name": "Product name cannot be empty",
  "stock": "Product stock cannot be negative"
}
```


## 🔐 Security
This application uses Basic Authentication along with role-based access control

### 🔑 Authentication

All protected endpoints require HTTP Basic authentication using a valid username and password.

- Passwords are encrypted using `BCrypt`
- A custom `UserDetailsService` is used to load users from the database
- Roles are used to control access (`ADMIN`, `REGULAR`)

### 👤 Default Admin User
A default `admin` user is inserted when application starts for testing purposes.


| Username | Password   | Role  |
|----------|------------|-------|
| admin    | admin123   | ADMIN |

### 📝 Example request

You can test secured endpoints using Basic Auth.

```bash
  curl -u admin:admin123 http://localhost:8080/products
```

## 🛠 Installation steps

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
