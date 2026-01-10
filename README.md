cat <<EOF > README.md
# ProductServices

Spring Boot based Product Service for an eCommerce platform.

## Features
- Get single product
- Get all products
- Get products by category
- Get all categories
- Create product
- Update product
- Delete product

## Tech Stack
- Java 17
- Spring Boot 3.2.x
- Maven
- RestTemplate
- FakeStore API

## API Endpoints

| Method | Endpoint | Description |
|------|---------|-------------|
| GET | /products | Get all products |
| GET | /products/{id} | Get product by id |
| GET | /products/category/{category} | Get products by category |
| GET | /products/categories | Get all categories |
| POST | /products | Create product |
| PUT | /products/{id} | Update product |
| DELETE | /products/{id} | Delete product |

## Notes
- FakeStore API is used as a third-party mock API
- POST, PUT, DELETE operations may not persist data permanently

## How to Run
\`\`\`bash
mvn spring-boot:run
\`\`\`
EOF
