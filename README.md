# Customer Service

Customer Management Microservice built using modern Spring Boot microservices architecture.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security
- JWT Authentication
- PostgreSQL
- Redis Cache
- RabbitMQ
- Zipkin Distributed Tracing
- Docker
- GitHub Actions CI/CD
- Karate API Testing
- Junit,Mockito Testing
- Swagger / OpenAPI

---

## Architecture

Client
→ Customer Service
→ PostgreSQL

Customer Service
→ Redis Cache

Customer Service
→ RabbitMQ

Customer Service
→ Zipkin

---

## Features

- User Registration
- User Login
- JWT Authentication
- Customer CRUD Operations
- Redis Caching
- RabbitMQ Event Publishing
- Distributed Tracing using Zipkin
- Dockerized Deployment
- GitHub Actions CI Pipeline
- Karate API Tests
- Junit,Mockito Tests
- Swagger API Documentation

---

## Project Structure

src

├── main

│ ├── java

│ │ └── com.alisha.customerservice

│ │ ├── controller

│ │ ├── service

│ │ ├── repository

│ │ ├── entity

│ │ ├── dto

│ │ ├── security

│ │ ├── exception

│ │ ├── config

│ │ └── CustomerserviceApplication

│ └── resources

│ ├── application.properties

│ ├── application-dev.properties

│ ├── application-docker.properties

│ └── keys

│

└── test

├── java

├── karate

└── resources

---

## API Documentation

Swagger UI

http://localhost:8081/swagger-ui/index.html

OpenAPI Specification

http://localhost:8081/v3/api-docs

---

## Authentication APIs

### Register User

POST /auth/register

Request

```json
{
  "name": "Aasif",
  "email": "aasif@gmail.com",
  "phone": "9999999999",
  "username": "aasif",
  "password": "Password123"
}
```

Response

```text
User Registered Successfully
```

---

### Login User

POST /auth/login

Request

```json
{
  "username": "aasif",
  "password": "Password123"
}
```

Response

```text
JWT Token
```

---

## Customer APIs

### Get Customer

GET /api/customers/{id}

Authorization

```text
Bearer <JWT_TOKEN>
```

Example

```text
GET /api/customers/1
```

---

### Get All Customers

GET /api/customers

Authorization

```text
Bearer <JWT_TOKEN>
```

---

### Update Customer

PUT /api/customers/{id}

Authorization

```text
Bearer <JWT_TOKEN>
```

Request

```json
{
  "name": "Updated Name",
  "email": "updated@gmail.com",
  "phone": "9999999999"
}
```

---

### Delete Customer

DELETE /api/customers/{id}

Authorization

```text
Bearer <JWT_TOKEN>
```

---

## Local Development Setup

### Prerequisites

- Java 17
- Maven
- PostgreSQL
- Redis
- RabbitMQ
- Zipkin
- Docker Desktop

---

### Start Infrastructure

PostgreSQL

```bash
docker run -d --name postgres \
-p 5432:5432 \
-e POSTGRES_PASSWORD=Sql@1234 \
-e POSTGRES_DB=customerdb \
postgres
```

Redis

```bash
docker run -d --name redis \
-p 6379:6379 \
redis
```

RabbitMQ

```bash
docker run -d --name rabbitmq \
-p 5672:5672 \
-p 15672:15672 \
rabbitmq:3-management
```

RabbitMQ Dashboard

http://localhost:15672

Username

```text
guest
```

Password

```text
guest
```

Zipkin

```bash
docker run -d --name zipkin \
-p 9411:9411 \
openzipkin/zipkin
```

Zipkin Dashboard

http://localhost:9411

---

### Build Project

```bash
mvn clean install
```

---

### Run Application

```bash
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Application URL

```text
http://localhost:8081
```

---

## Docker Deployment

### Build Docker Image

```bash
docker build -t customer-service .
```

---

### Run Docker Container

```bash
docker run -d \
--name customer-service \
-p 8082:8081 \
-e SPRING_PROFILES_ACTIVE=docker \
-e DB_HOST=host.docker.internal \
-e REDIS_HOST=host.docker.internal \
-e RABBITMQ_HOST=host.docker.internal \
-e ZIPKIN_HOST=host.docker.internal \
customer-service
```

Application URL

```text
http://localhost:8082
```

---

## GitHub Actions CI Pipeline

Workflow Location

```text
.github/workflows/customer-service-ci.yml
```

Pipeline Steps

1. Checkout Source Code
2. Setup JDK 17
3. Execute Maven Tests
4. Execute Karate Tests
5. Build Spring Boot Jar
6. Build Docker Image
7. Validate Build

---

## Testing

### Maven Tests - Except Karate

```bash
mvn test
```

### Full Build

```bash
mvn clean install
```

---

### Karate Tests

```bash
mvn test -Dtest=KarateRunner
```

Karate feature files are located under:

```text
src/test/java/karate
```

---

## Profiles

### Development Profile

```text
application-dev.properties
```

Used for local development.

---

### Docker Profile

```text
application-docker.properties
```

Used when running application inside Docker containers.

---

### Test Profile

```text
application-test.properties
```

Uses H2 in-memory database for automated testing and CI pipelines.

---

## Monitoring

### Actuator

Health Endpoint

```text
http://localhost:8081/actuator/health
```

Metrics Endpoint

```text
http://localhost:8081/actuator/metrics
```

---

### Zipkin Tracing

Zipkin UI

```text
http://localhost:9411
```

Trace endpoint configured through:

```properties
management.zipkin.tracing.endpoint
```

---

## Logging

Log File

```text
logs/customer-service.log
```

Features

- Rolling Logs
- TraceId Logging
- SpanId Logging
- Daily Log Rotation

---

## Future Enhancements

- API Gateway
- Transaction Service
- Notification Service
- Centralized Logging (Loki / ELK)
- Kubernetes Deployment
- SonarQube Integration
- Prometheus Metrics
- Grafana Dashboards
- Config Server
- Service Discovery

---

## Author

Alisha Anjum

Customer Service Microservice Project
