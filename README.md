# 🚀 Product Services API

A production-ready **Spring Boot backend application** for managing Products and Categories with enterprise-grade features like caching, soft delete, scheduling, reporting, database migrations, and API documentation.

This project demonstrates **real-world backend engineering practices**, not just basic CRUD operations.

---

## 🧰 Tech Stack

- Java 17+
- Spring Boot 3
- Spring Data JPA (Hibernate)
- MySQL
- Redis (Caching)
- Flyway (DB Migrations)
- Swagger / OpenAPI
- Scheduler (Cron Jobs)
- Lombok
- Maven

---

## ✨ Features

### ✅ Product Management
- Create product
- Update product
- Get product by id
- Get all products
- Category based filtering

### ⚡ Performance
- Redis caching using:
    - `@Cacheable`
    - `@CachePut`
    - `@CacheEvict`

### 🧠 Soft Delete System
- No physical deletes
- Uses `is_deleted` flag
- Restore deleted products anytime

### 📊 Reporting System
- Daily scheduled job (Cron)
- Generates:
    - total products
    - average price
- Stores results in `product_report` table

### 📑 Pagination & Sorting
Example:
/products/paginated?page=0&size=10&sort=price


### 🗃 Database Safety
- Flyway migrations
- Version-controlled schema
- No manual SQL setup

### 📚 API Documentation
- Swagger UI integration
- Test APIs directly from browser

---

## 📂 Project Structure

src/main/java/dev/jay/productservices
│
├── controllers → REST APIs
├── services → Business logic
├── repositories → Database queries
├── models → Entities
├── dtos → Request/Response models
├── schedulers → Cron jobs
├── configs → Swagger/Redis configs
└── db/migration → Flyway SQL files


---

## ⚙️ Setup Instructions

### 1. Clone
git clone <your-repo-url>
cd ProductServices


---

### 2. Create MySQL DB
CREATE DATABASE productservice;


---

### 3. Start Redis
Mac:
brew services start redis


Linux:
redis-server


---

### 4. Update application.properties
spring.datasource.username=your_user
spring.datasource.password=your_password


---

### 5. Run project
mvn spring-boot:run


---

## 🌐 Swagger API Docs

Open in browser:

http://localhost:8080/swagger-ui/index.html


You can:
- View endpoints
- Test APIs
- Send requests directly

---

## 🔥 Important Endpoints

### Create product
POST /products


### Get product
GET /products/{id}


### Get all products
GET /products


### Pagination
GET /products/paginated?page=0&size=10


### Soft delete
DELETE /products/{id}


### Restore product
PUT /products/restore/{id}


### Reports
GET /reports


---

## 🧠 Key Concepts Used

### Soft Delete
is_deleted = true

Prevents data loss.

### Redis Cache
Improves performance by reducing DB hits.

### Flyway
Maintains DB schema consistency.

### Scheduler (Cron)
Automatically generates daily reports.

---

## 👨‍💻 Author

Jayachandra Burla  
Backend Developer 
---

## ⭐ Why this project?

This project demonstrates:

✔ Clean architecture  
✔ Enterprise backend design  
✔ Performance optimization  
✔ Production best practices  
✔ Interview-ready implementation

If you found this helpful, please ⭐ the repo!