# 📚 Library Management REST API

A robust RESTful web service built with **Spring Boot** and **PostgreSQL** for managing library resources, user accounts, and book rentals. This project was developed as the final capstone for an intensive 104-hour Java Backend Academy.

## 🚀 Core Features & Business Logic

* **Rental State Machine:** Implemented a strict lifecycle management system for book rentals. Rentals safely transition through predefined states (`PENDING` -> `IN_PROGRESS` -> `FINISHED` / `CANCELED` / `DELAYED`) preventing illegal data modifications.
* **Role-Based Access Control (RBAC):** API endpoints are secured using **Keycloak** (OAuth2), distinguishing between roles and privileges.
* **Database Optimization:** Resolved common Hibernate N+1 query performance issues using `JOIN FETCH` for complex entity relationships.
* **Pagination & Sorting:** Built scalable search endpoints utilizing Spring Data JPA's `Pageable`.
* **Automated Background Jobs:** Configured Spring `@Scheduled` Cron Jobs to automatically monitor overdue rentals and trigger email notifications.
* **Cross-Field Validation:** Applied strict data validation mechanisms via Spring Boot Starter Validation.

## 🗄️ Domain Architecture

The application models a real-world library system with the following core entities:
* **User:** Can manage a personal `Wishlist`, leave a `Review`, and create `Rentals`.
* **Book & Library:** Books are linked to Libraries and Publishers, tracking popularity via a `rental_contor`.
* **Rental:** Acts as the transactional core, binding Users and Books under strict time and state constraints.

## 🛠️ Tech Stack

* **Language:** Java (JDK 17)
* **Framework:** Spring Boot (Spring Web, Spring Data JPA, Spring Security)
* **Database:** PostgreSQL & Hibernate ORM
* **IAM / Security:** Keycloak
* **Tools:** Maven, Git, Postman (API Testing)

## ⚙️ Local Setup

1. **Clone the repository:**
   `git clone https://github.com/danielDanielq/book-rental-api.git`
2. **Configure Environment:**
   Ensure PostgreSQL and Keycloak servers are running locally or via Docker. Update the `src/main/resources/application.properties` with your database credentials.
3. **Run the application:**
   `mvn spring-boot:run`