# Login Page System
A secure user authentication and management system built with Spring Boot, MongoDB, and Redis.

## Features
* User registration with email verification via OTP
* Secure login with JWT authentication
* Role-based access control (User and Admin roles)
* Password encryption using BCrypt
* OTP verification via email
* Redis for caching and temporary data storage
* MongoDB for persistent user data storage

## Technology Stack
* Java 21 with preview features
* Spring Boot 3.3.5
* Spring Security for authentication and authorization
* JWT (JSON Web Token) for stateless authentication
* MongoDB for database
* Redis for caching and OTP storage
* Spring Mail for email services
* Lombok for reducing boilerplate code
* SonarQube for code quality analysis

## Project Structure
The application follows a layered architecture:

* **Controllers:** Handle HTTP requests and responses
* **Services:** Implement business logic
* **Repositories:** Interface with the MongoDB database
* **Config:** Configuration classes for Spring, Security, Redis, etc.
* **Entity:** Domain models that map to MongoDB documents
* **Util:** Utility classes

## API Endpoints

### Public Endpoints
* _**POST /signup:**_ Register a new user
* _**POST /login:**_ Authenticate a user and return JWT
* _**POST /verify:**_ Verify OTP sent to user's email

### User Endpoints (Authenticated)
* _**GET /users:**_ Get all users
* _**PUT /users:**_ Update user information
* _**DELETE /users:**_ Delete current user

### Admin Endpoints (Admin Role Required)
* _**POST /admin:**_ Add a new admin user
* _**GET /admin:**_ Get all users

## Authentication Flow

* User registers with username, email, and password
* System sends an OTP to the user's email
* User verifies their email by submitting the OTP
* Upon successful verification, user data is stored in MongoDB
* For login, user provides credentials and receives a JWT
* JWT is used for subsequent authenticated requests

## Setup Instructions
### Prerequisites

* Java 21
* MongoDB
* Redis
* Maven

## Security Considerations

* Passwords are encrypted using BCrypt with a strength of 12
* JWT tokens expire after 10 minutes
* OTPs expire after 5 minutes (300 seconds)
* CSRF protection is disabled for API usage
* Secure random generation is used for OTP creation
