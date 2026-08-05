# 📚 Book Rental API (Library Management System)

A robust and secure RESTful API built to automate and manage the entire lifecycle of a book rental system. This project demonstrates enterprise-level backend architecture, featuring strict state management, secure token-based authentication, and database performance optimizations.

## Tech Stack & Technologies

* **Core:** Java 8+, Spring Boot (Spring Web, Spring Data JPA)
* **Database & ORM:** PostgreSQL, Hibernate ORM
* **Security:** Keycloak, OAuth2, JSON Web Tokens (JWT), Role-Based Access Control (RBAC)
* **Tools:** Docker, Postman, Git/GitHub

## Key Features

* **State Machine Implementation:** Architected a strict state machine to handle the rental lifecycle securely (e.g., *Available -> Rented -> Overdue -> Returned*).
* **Enterprise-Grade Security:** Secured REST endpoints using token-based authentication via **Keycloak**. Implemented RBAC to differentiate permissions between standard users and library admins.
* **Database Optimization:** Optimized performance and reduced latency by solving N+1 query problems utilizing `JOIN FETCH` and implementing proper data pagination.
* **Automated Background Jobs:** Configured **Spring Schedulers** to run asynchronous background tasks, such as tracking overdue books and triggering automated email notifications.


### Prerequisites
* Java 17 (or compatible version)
* Maven
* PostgreSQL installed and running
* Keycloak server for authentication

## System Architecture Notes
This project was built following SOLID design principles and emphasizes clean code architecture, ensuring scalability and maintainability for future feature integrations.
