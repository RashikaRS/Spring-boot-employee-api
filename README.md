# Employee API — Spring Boot REST + Hibernate/JPA

Spring Boot 3 (Java 17) REST API with Spring Data JPA, MySQL/PostgreSQL and Jakarta Validation.

## Features
- JPA entities with `@Entity`, `@Table`, `@OneToMany` (Department) and `@ManyToOne` (Employee)
- `JpaRepository` interfaces with custom `@Query` methods
- `@RestController` endpoints: GET, POST, PUT, DELETE
- Request validation: `@NotNull`, `@NotBlank`, `@Size`, `@Email`, `@Positive`
- Global exception handling (400 / 404 / 409 with clean JSON)

## Project structure
```
controller/  -> REST endpoints
service/     -> business logic
repository/  -> Spring Data JPA + @Query
entity/      -> Department, Employee
dto/         -> request/response + validation
exception/   -> custom exceptions + @RestControllerAdvice
```

## Run
1. `CREATE DATABASE employee_db;` in MySQL
2. Update username/password in `src/main/resources/application.properties`
3. `mvn spring-boot:run` (runs on http://localhost:8080)

## Endpoints
| Method | URL | Description |
|---|---|---|
| GET | /api/departments | List departments |
| GET | /api/departments/{id} | Get one |
| POST | /api/departments | Create |
| PUT | /api/departments/{id} | Update |
| DELETE | /api/departments/{id} | Delete (cascades employees) |
| GET | /api/employees | List employees |
| GET | /api/employees/{id} | Get one |
| GET | /api/employees/department/{deptId} | By department |
| GET | /api/employees/search?keyword=ra | Search by name |
| GET | /api/employees/salary?min=30000 | Salary >= min |
| POST | /api/employees | Create |
| PUT | /api/employees/{id} | Update |
| DELETE | /api/employees/{id} | Delete |

## Sample requests
```json
POST /api/departments
{ "name": "Engineering", "description": "Software team" }

POST /api/employees
{ "name": "Rashika", "email": "rashika@example.com", "salary": 45000, "departmentId": 1 }
```
