# E-Commerce Microservices

A microservices-based e-commerce backend built with Java Spring Boot, PostgreSQL, and Docker.  
The system is designed with a clean, layered architecture and supports communication between independent services via REST (later extendable to Feign Client and RabbitMQ for event-driven patterns).  

---

## Features
- **Product Service**  
  - Manage products (CRUD)  
  - Atomic stock increase/decrease  
  - Unique product names enforced  

- **Customer Service** (in progress)  
  - Manage customers (CRUD)  
  - Validation on email (unique)  
  - Address fields included (street, city, state, postal code, country)  

- **Order Service** (planned)  
  - Manage orders with multiple order items  
  - Customer validation on order creation  
  - Product stock reservation and compensation  
  - Total amount calculation  

- **Future Enhancements**  
  - Invoice generation (PDF) and Email sending  
  - Shopping cart mechanism  
  - Order lifecycle (Created, Paid, Shipped, Delivered, Cancelled)  
  - Flyway migrations for DB schema versioning  
  - Docker Compose for multi-service orchestration  
  - Security (JWT-based authentication and authorization)  
  - Notification service (RabbitMQ for async events)  

---

## Architecture
```
microservices-ecommerce/
│
├── product-service/      # Product management (CRUD + stock)
├── customer-service/     # Customer management (CRUD + validation) [in progress]
├── order-service/        # Order & OrderItem management [planned]
│
└── common future setup:
    - Docker Compose (PostgreSQL DBs + services)
    - Flyway DB migrations
    - Spring Security
    - Event-driven notifications (RabbitMQ)
```

- Each service has its own PostgreSQL database.  
- Services communicate via REST APIs (later Feign/RabbitMQ).  
- Layered structure per service:  
  ```
  controller/   → REST endpoints  
  service/      → business logic  
  repository/   → database access  
  entity/       → JPA entities  
  dto/          → request/response DTOs  
  ```

---

## Technologies
- Java 24 + Spring Boot 3.x  
- PostgreSQL 16 (Dockerized)  
- Spring Data JPA (Repository abstraction)  
- Spring Validation (DTO validation)  
- Lombok (boilerplate reduction)  
- Maven (build tool)  
- Docker (containerized DBs, later docker-compose)  
- Flyway (database migrations – planned)  
- RabbitMQ (async event communication – planned)  
- Spring Security (JWT auth – planned)  

---

## Current Endpoints (Product-Service Example)
**Product Service (port: 8080)**  
- `POST   /api/products` → create product  
- `GET    /api/products/{id}` → get product by id  
- `GET    /api/products` → list all products  
- `PUT    /api/products/{id}` → update product  
- `DELETE /api/products/{id}` → delete product  
- `PUT    /api/products/{id}/decrease?qty=N` → decrease stock (atomic)  
- `PUT    /api/products/{id}/increase?qty=N` → increase stock  

**Customer Service (port: 8081)** (in progress)  
- CRUD endpoints planned (create, get by id, list, update, delete)  

**Order Service (port: 8082)** (planned)  

---

## Docker Setup
Each service has its own PostgreSQL container. Example for customer-service:

```bash
docker run --name ecom-customer-db   -e POSTGRES_USER=<your-username>   -e POSTGRES_PASSWORD=<your-password>   -e POSTGRES_DB=customer_db   -p 5435:5432   -v ecom-customer-db-data:/var/lib/postgresql/data   -d postgres:16
```

---

## Roadmap
- [x] Product-Service setup with CRUD and stock management  
- [ ] Customer-Service setup with CRUD (in progress)  
- [ ] Order-Service setup (Order + OrderItem entities)  
- [ ] REST communication between services (order → product, customer)  
- [ ] Exception Handling (Global handler and custom exceptions)  
- [ ] Flyway migrations  
- [ ] Docker Compose setup  
- [ ] Security (JWT authentication)  
- [ ] Invoice generation (PDF and Email)  
- [ ] RabbitMQ event-driven notifications  
