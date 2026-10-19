# Corporate Expense Management & Approval System

![Java](https://img.shields.io/badge/Java-17-orange.svg?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg?logo=springboot)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data-JPA-blue.svg)
![TiDB Cloud](https://img.shields.io/badge/TiDB%20Cloud-Serverless%20MySQL-f39c12.svg)
![Render](https://img.shields.io/badge/Deployment-Render-46E3B7.svg?logo=render)
![Docker](https://img.shields.io/badge/Container-Docker-2496ED.svg?logo=docker)
![OpenAPI / Swagger](https://img.shields.io/badge/API%20Docs-Swagger%20UI-85EA2D.svg?logo=swagger)
![License](https://img.shields.io/badge/License-MIT-lightgrey.svg)

A modular, enterprise-grade RESTful backend system built with Java 17 and Spring Boot 3 to streamline corporate expense claims, departmental budget limits, and hierarchical manager approvals. The application is containerized with Docker, deployed continuously to Render, and persists relational data on TiDB Cloud Serverless (MySQL-compatible).

---

## Live Demo & API Documentation

- **Live Application / Base URL:** `https://expense-manager-api.onrender.com`
- **Interactive Swagger UI Documentation:** `https://expense-manager-api.onrender.com/swagger-ui/index.html`

> **Note on Render Free Tier:** Render spins down free containers after 15 minutes of idle time. The initial request to a sleeping instance may take approximately 30–50 seconds to warm up. Subsequent requests respond instantly.

---

## Key Features

- **Employee & Department Modeling:** Hierarchical management structure linking employees to specific departmental expense policies and budget ceilings.
- **Atomic State Transitions:** Safe state transitions (`PENDING`, `APPROVED`, `REJECTED`) enforced via Spring `@Transactional` boundaries to eliminate partial database writes and duplicate approvals.
- **Relational Data Mapping:** Domain relationships engineered with JPA/Hibernate annotations (`@OneToMany`, `@ManyToOne`) ensuring referential integrity across Employees, Expenses, and Departments.
- **Robust Input Validation:** Strict request payload auditing using Bean Validation annotations (`@Valid`, `@NotNull`, `@Min`, `@NotBlank`) with human-readable error contracts.
- **Centralized Exception Handling:** Uniform error responses via `@ControllerAdvice` and `@ExceptionHandler`, catching business logic violations and malformed DTO submissions cleanly.
- **Cloud-Native Deployment Pipeline:** Containerized through a multi-stage Docker build with constrained JVM memory flags (`-Xmx256m -Xss512k`) and dynamic `$PORT` binding for Render hosting.

---

## Database Schema Design

<img width="898" height="347" alt="image" src="https://github.com/user-attachments/assets/a3cca61d-b20b-4227-8a8b-d9811b5ccfd0" />

---

## Tech Stack

| Component | Technology / Library |
| :--- | :--- |
| **Language** | Java 17 (Eclipse Temurin) |
| **Framework** | Spring Boot 3.x (Web, Data JPA, Validation) |
| **Database** | TiDB Cloud Serverless (MySQL 8.0 Protocol) |
| **Connection Pool** | HikariCP |
| **ORM & Persistence** | Hibernate 6.x |
| **API Documentation** | SpringDoc OpenAPI / Swagger UI |
| **Containerization** | Docker (Multi-stage build) |
| **Hosting & CI/CD** | Render (Web Service via GitHub webhooks) |
| **Build & Dependencies** | Apache Maven 3.9+ |
| **Version Control** | Git & GitHub |

---

## API Endpoints Summary

### Employees Management

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/employees` | Register a new employee | `201 Created` |
| `GET` | `/api/employees` | Retrieve a list of all active employees | `200 OK` |
| `GET` | `/api/employees/{id}` | Fetch profile of an employee by Id | `200 OK` / `404 Not Found` |
| `DELETE` | `/api/employees/{id}` | Remove an employee by Id | `200 OK` |
| `PUT` | `/api/employees/{id}` | Update an employee details by Id | `200 OK` / `404 Not Found` |
| `GET` | `/api/employees/department/{department}` | Fetch all employees in a department  | `200 OK` |

### Expenses & Approvals

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/expenses` | Submit a new expense claim under an employee | `200 OK` |
| `GET` | `/api/expenses` | Retrieve all submitted expenses | `200 OK` |
| `GET` | `/api/expenses/status/{status}` | Filter expenses by status (Pending, Approved, Rejected) | `200 OK` / `404 Not Found` |
| `PATCH` | `/api/expenses/{id}/status` | Manager action: Update claim status (`APPROVED`/`REJECTED`) | `200 OK` |
| `PUT` | `/api/expenses/{id}` | Edit an existing expense report (only if Pending) | `200 OK` |
| `DELETE` | `/api/expenses/{id}` | Delete an existing expense report (only if Pending) | `200 OK` |

---

## Local Setup & Development

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.8+
- Git
- Access to an active MySQL database instance or TiDB Cloud Serverless cluster

### 1. Clone Repository
Bash
```
git clone https://github.com/ritik-codes/expense-manager.git
```

### 2. Configure Environment Variables
On Windows Command Prompt
``` 
set DB_URL=jdbc:mysql://<tidb-host>:4000/test?sslmode=verify-full
set DB_USERNAME=<your-username>
set DB_PASSWORD=<your-password>
```

### 3. Build & Run Application
Bash
```
mvn clean spring-boot:run
```
Once running, access your local Swagger UI at:
```
http://localhost:8080/swagger-ui/index.html
```

---

## Docker Containerization
The repository includes a production-ready, two-stage Dockerfile designed to optimize image layer caching and minimize runtime container overhead.

Dockerfile
```
# Stage 1: Build the JAR with Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with lightweight JRE and memory limits
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Xmx256m -Xss512k -jar app.jar"]
```
### Build & Run Container Locally
Bash
```
# Build the Docker image
docker build -t expense-manager-api .

# Run the container locally passing environment variables
docker run -p 8080:8080 \
  -e DB_URL="jdbc:mysql://<tidb-host>:4000/test?sslmode=verify-full" \
  -e DB_USERNAME="<your-username>" \
  -e DB_PASSWORD="<your-password>" \
  expense-manager-api
```

---

### License
This project is licensed under the MIT License.
