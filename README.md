# E-Commerce Microservices

A microservices-based e-commerce backend built with **Java 24**, **Spring Boot 3.2.x**, and **PostgreSQL**.  
Services are independent, each with its own database, and communicate via **REST** using `WebClient`.

---

## Services & Ports

| Service              | Port  | Description                                  |
|----------------------|------:|----------------------------------------------|
| **customer-service** |  8080 | Customer CRUD, email uniqueness, addresses   |
| **product-service**  |  8081 | Product CRUD, atomic stock inc/dec           |
| **order-service**    |  8082 | Orders with multiple items; totals & status  |

> Each service has its **own PostgreSQL** database (example ports below).

---

## Architecture

- **Per service layers**
  - `controller/` → REST endpoints  
  - `service/` → business logic  
  - `repository/` → Spring Data JPA  
  - `entity/` → JPA entities  
  - `dto/` → request/response DTOs
- **Data model (order-service)**
  - `Order` (master) ↔ `OrderItem` (one-to-many)
  - `OrderItem` persists `productId`, `productName`, `unitPrice`, `quantity`, `lineTotal`
  - `Order.totalAmount` = sum of item `lineTotal`s
- **Inter-service communication**
  - `order-service` → `customer-service` & `product-service` via **Spring WebClient (WebFlux)**

---

## Tech Stack

- **Spring Boot 3.2.x** (Web, Validation, Data JPA, WebFlux for `WebClient`)
- **PostgreSQL 16**
- **Hibernate 6**, **HikariCP**
- **Lombok** (annotation processors enabled)
- **Maven**

---

## Build & Run

```bash
# Build all modules
mvn clean install

# Run a single service
mvn -pl customer-service spring-boot:run
mvn -pl product-service  spring-boot:run
mvn -pl order-service    spring-boot:run
```

### Local PostgreSQL (examples)

```bash
# product-service DB (maps host 5434 -> container 5432)
docker run --name ecom-product-db   -e POSTGRES_USER=youruser   -e POSTGRES_PASSWORD=yourpass   -e POSTGRES_DB=product_db   -p 5434:5432 -v ecom-product-db-data:/var/lib/postgresql/data   -d postgres:16

# customer-service DB (maps 5435)
docker run --name ecom-customer-db   -e POSTGRES_USER=youruser   -e POSTGRES_PASSWORD=yourpass   -e POSTGRES_DB=customer_db   -p 5435:5432 -v ecom-customer-db-data:/var/lib/postgresql/data   -d postgres:16

# order-service DB (maps 5436)
docker run --name ecom-order-db   -e POSTGRES_USER=youruser   -e POSTGRES_PASSWORD=yourpass   -e POSTGRES_DB=order_db   -p 5436:5432 -v ecom-order-db-data:/var/lib/postgresql/data   -d postgres:16
```

---

## Configuration

### Common (JPA, datasource)
Each service has its own `application-local.properties` similar to:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
```

### product-service
```properties
server.port=8081

spring.datasource.url=jdbc:postgresql://localhost:5434/product_db
spring.datasource.username=youruser
spring.datasource.password=yourpass
```

### customer-service
```properties
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5435/customer_db
spring.datasource.username=youruser
spring.datasource.password=yourpass
```

### order-service
```properties
server.port=8082

spring.datasource.url=jdbc:postgresql://localhost:5436/order_db
spring.datasource.username=youruser
spring.datasource.password=yourpass

# Base URLs for cross-service calls (required)
customer.service.base-url=http://localhost:8080
product.service.base-url=http://localhost:8081
```

---

## API Overview

### product-service (`/api/products`)
- `POST /` — create product  
- `GET /{id}` — get product by id  
- `GET /` — list products  
- `PUT /{id}` — update product  
- `DELETE /{id}` — delete product  
- `PUT /{id}/decrease?qty=N` — **atomic** stock decrease  
- `PUT /{id}/increase?qty=N` — stock increase

### customer-service (`/api/customers`)
- `POST /` — create customer (unique email)  
- `GET /{id}` — get by id  
- `GET /` — list customers  
- `PUT /{id}` — update customer  
- `DELETE /{id}` — delete customer

### order-service (`/api/orders`)
- `POST /` — **create order**  
  - Validates customer via `customer-service`
  - Fetches products via `product-service`
  - Persists `Order` + `OrderItem`s with `productName` & `lineTotal`
  - Computes `totalAmount`
- `GET /{id}` — **find order by id**

#### Example — Create Order (Postman body)
```json
{
  "customerId": 1,
  "items": [
    { "productId": 3, "quantity": 2 },
    { "productId": 5, "quantity": 1 }
  ]
}
```

#### Example — Order Response (simplified)
```json
{
  "id": 12,
  "customerId": 1,
  "totalAmount": 129.97,
  "status": "PENDING",
  "createdAt": "2025-08-26T12:34:56Z",
  "items": [
    { "productId": 3, "productName": "Wireless Mouse", "unitPrice": 29.99, "quantity": 2, "lineTotal": 59.98 },
    { "productId": 5, "productName": "Keyboard",       "unitPrice": 69.99, "quantity": 1, "lineTotal": 69.99 }
  ]
}
```

---

## Current Status

- ✅ **product-service**: CRUD + stock ops; unique product name  
- ✅ **customer-service**: CRUD + email uniqueness + address fields  
- ✅ **order-service**: create & get-by-id; totals, item lineTotals, productName persisted; REST calls via `WebClient`  
- ⏳ **RabbitMQ integration**: planned for order events (e.g., `order.created`)  

---

## Future Enhancements (Upcoming)

- **AI service**

---

## Repository Structure

```
ecommerce-microservices/
│
├── common-dto/          # Shared DTOs (records/classes)
├── product-service/     # Product management
├── customer-service/    # Customer management
├── order-service/       # Orders & OrderItems
│
└── pom.xml              # Parent (dependency mgmt, modules)
```

---

## Notes

- Ensure **Lombok** annotation processing is enabled in your IDE.
- Because `order-service` calls other services, verify `customer.service.base-url` and `product.service.base-url` are correct and the target services are running.
