# Finance Dashboard Backend

Built this as a take-home assignment for Zorvyn. It's a backend API for a finance dashboard
where users interact with financial data based on their role.

## What I Built

- Users can self-register and get access as a VIEWER by default
- Admin has full control — create users, assign roles, manage financial records
- Analysts can view records and access dashboard insights
- All users can access the dashboard — total income, expenses, net balance,
  category wise totals, recent activity, and weekly/monthly trends
- Financial records can be filtered by type, category, and date
- Deleted records are not permanently removed — soft delete keeps the data intact
  since this is financial data and audit trails matter


## Architecture

The application follows a layered architecture:

- Controller layer handles HTTP requests  
- Service layer contains business logic  
- Repository layer interacts with the database


## Why I Made Certain Decisions

- **Admin controls everything** — financial data is sensitive, so I kept full control  
  with the admin rather than letting users manage themselves

- **VIEWER by default on register** — anyone can register but gets minimal access  
  Admin decides who gets more

- **Soft delete for records** — financial records should never be permanently deleted  
  deletedAt timestamp marks them as deleted but keeps them in DB

- **HTTP Basic Auth** — prioritized simplicity and faster implementation under time constraints  
  design allows easy migration to JWT for production use

- **Default admin on startup** — a DataSeeder creates the first admin automatically  
  so the system is usable from the start without any manual DB inserts

## Tradeoffs

- Roles, record types, and categories are plain strings. I considered using enums
  for stronger typing but kept strings to stay flexible and move faster.
  Enums would be the right call in a production system
- No pagination implemented — can be added with Spring Data's Pageable
- No unit tests — given the time constraint I focused on getting the core working correctly

## Tech Stack

- Java 21, Spring Boot 4
- Spring Security — HTTP Basic Authentication
- Spring Data JPA + Hibernate + MySQL
- Lombok, Bean Validation
- SpringDoc OpenAPI (Swagger UI)

## How to Run

1. Clone the repo
2. Create a MySQL database named `finance_db`
3. Create `application.properties` (see Configuration section below)
4. Run the app — Hibernate creates tables automatically
5. Default admin is created on startup:
    - Email: `admin@zorvyn.com`
    - Password: `Admin@123`


## ⚠️ Configuration

This project does not include `application.properties` for security reasons.

Create `src/main/resources/application.properties` and add:

```properties
#Database properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#JPA properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

springdoc.swagger-ui.persist-authorization=true

```


## API Documentation

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Quick Testing

Start the application and open Swagger UI:

http://localhost:8080/swagger-ui/index.html

Use default admin credentials to test secured endpoints.

## Endpoints

### Authentication
| Method | Endpoint                     | Access | Description        |
|--------|------------------------------|--------|--------------------|
| POST   | /api/authentication/register | Public | Register as VIEWER |

### Users
| Method | Endpoint              | Access | Description           |
|--------|-----------------------|--------|-----------------------|
| POST   | /api/user/add         | ADMIN  | Create user with role |
| GET    | /api/user             | ADMIN  | Get all users         |
| GET    | /api/user/{id}        | ADMIN  | Get user by ID        |
| PATCH  | /api/user/update/{id} | ADMIN  | Update user           |
| DELETE | /api/user/delete/{id} | ADMIN  | Delete user           |

### Financial Records
| Method | Endpoint                              | Access | Description                 |
|--------|---------------------------------------|--------|-----------------------------|
| POST   | /api/record/create                    | ADMIN  | Create record               |
| GET    | /api/record                           | ALL    | Get all records             |
| GET    | /api/record/{id}                      | ALL    | Get record by ID            |
| PATCH  | /api/record/update/{id}               | ADMIN  | Update record               |
| DELETE | /api/record/delete/{id}               | ADMIN  | Soft delete record          |
| GET    | /api/record/filter/type?type=         | ALL    | Filter by type              |
| GET    | /api/record/filter/category?category= | ALL    | Filter by category          |
| GET    | /api/record/filter/date?date=         | ALL    | Filter by date (yyyy-MM-dd) |

### Dashboard
| Method | Endpoint               | Access | Description                       |
|--------|------------------------|--------|-----------------------------------|
| GET    | /api/dashboard/summary | ALL    | Full summary with category totals |
| GET    | /api/dashboard/weekly  | ALL    | Last 7 days trends                |
| GET    | /api/dashboard/monthly | ALL    | Last 30 days trends               |


## ⚠️ Note on Versions

This project was initially submitted mentioning:

- Java 17  
- Spring Boot 3  

However, the current implementation uses:

- Java 21  
- Spring Boot 4  

Please ensure the correct versions are used while running the project.


## GitHub

https://github.com/kranthij58/finance-dashboard-backend
