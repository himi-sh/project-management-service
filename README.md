# Product Management Service

This is a RESTful microservice for managing Products in an inventory system, built with Spring Boot, PostgreSQL, and Docker.

## Requirements

- Java 21
- Docker & Docker Compose
- Maven (optional, wrapper included/docker used)

## Getting Started

### Run with Docker Compose

The easiest way to run the application is using Docker Compose. This will start both the application and the PostgreSQL database.

```bash
docker-compose up --build
```

The application will be available at `http://localhost:8080`.

### Run Locally

1. Start a PostgreSQL database (or use `docker-compose up db -d`).
2. Update `src/main/resources/application.properties` if your DB credentials differ.
3. Run the application:

```bash
mvn spring-boot:run
```

## API Documentation

Swagger UI is available at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Endpoints

- `POST /products`: Create a new product
- `GET /products`: List all products (supports pagination `?page=0&size=10`)
- `GET /products/search?name=...`: Search products by name
- `PUT /products/{id}/quantity`: Update product quantity
- `DELETE /products/{id}`: Delete product
- `GET /products/summary`: Get inventory statistics

## Testing

Run unit tests:

```bash
mvn test
```

For integration tests involving Docker/Testcontainers:

```bash
mvn verify
```
