# wegrocerieapi

## Description
wegroceriesapi is a RESTful service designed to enable sellers, buyers, and delivery services to connect seamlessly for wholesale grocery transactions. It supports authentication, product management, order processing, and user management. The UserService module provides essential functionalities for user registration, retrieval, updating, and deletion. It ensures unique usernames and emails, maintains user records securely, and facilitates user identity validation. Built using Java and Spring Boot, this API adheres to a clean and modular structure for easy scalability, maintainability, and robust user management.

## Getting Started

### Clone Repository
1. Fork this repository in your GitHub account
2. Clone your fork locally or open in CodeSpaces.

```sh
git clone [REPO_URL]
cd [REPO_NAME]
```

**Note:** Replace [REPO_URL] with the link to your GitHub repository and [REPO_NAME] with the repository's name.

### Create Database
1. Login to MySQL:

```sh
mysql -u root -p
```
**Note:** If your root user doesn't have a password set, omit the `-p` flag.
2. Create a new database:

```sh
CREATE DATABASE IF NOT EXISTS wegroceriesapi;
exit;
```
### Initialise project
1. open your repository in VS Code
2. Add the following values to src/main/resources/application.properties:

```sh
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=true
spring.config.import=optional:./local.properties;
```
3. In order to prevent sensitive values from being committed to version control, add a new entry to the .gitignore file:

```sh
local.properties
```

4. Create a new file at src/main/resources/local.properties and paste in the following:

```sh
spring.datasource.url=jdbc:mysql://localhost:3306/springbootexercise

# Replace "root" with your database user, if applicable
spring.datasource.username=root

# Specify your database user's password, if applicable. If your database user doesn't have a password set, delete the line below
spring.datasource.password=YOUR_MYSQL_PASSWORD
``` 

5. Replace the username and password values with your MySQL credentials. IMPORTANT: Ensure there are no spaces before or after the password.

### Run Application
To start the API, run the following command:

#### macOS / Git Bash

```sh 
./mvnw spring-boot:run
```
#### Windows Command Prompt

```cmd
mvnw spring-boot:run
```

If successful, you should see output that ends similarly to the following:
2025-01-30T21:38:56.281Z  INFO 27184 --- [wegroceriesapi] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2025-01-30T21:38:56.281Z  INFO 27184 --- [wegroceriesapi] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
mcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2025-01-30T21:38:56.291Z  INFO 27184 --- [wegroceriesapi] [           main] c.w.w.WegroceriesapiApplication          : Started WegroceriesapiApplication in 4.127 seconds (process running for 4.461)

## IMPORTANT 
If everything is working correctly, the output will appear "stuck" and the command prompt won't return until you stop the application, which should now be running at http://localhost:8080/api/users.

## The JSON representation of an IOU that you'll get in responses or provide in the request body for POST and PUT requests will resemble the following:
/*
{
    "id": "359a0cdb-0a38-4f07-894f-a2a9bccd271d",
    "username": "amy",
    "email": "soso@example.com",
    "password": "securePass123",
    "firstName": "Amy",
    "lastName": "Solomon",
    "createdAt": "2025-02-11T19:57:36.355839300Z",
    "updatedAt": "2025-02-11T19:57:36.355839300Z"
} 

### Stop Application
Stop the application by pressing `Ctrl + C`

## API Endpoints
Below are the primary endpoints for Users, Products, and Orders:

### User Endpoints:
Method	Endpoint	Description	Auth Required
POST	/api/users/register	Register a new user	 No
POST	/api/users/login	Authenticate user No
GET	/api/users/{id}	Get user by ID	 Yes
PUT	/api/users/{id}	Update user details Yes
DELETE	/api/users/{id}	Delete user	 Yes

### Product Endpoints:
Method	Endpoint	Description	    Auth Required
POST	/api/products	Add a new product	 Yes
GET	/api/products	Retrieve all products	 No
GET	/api/products/{id}	Get product by ID	 No
PUT	/api/products/{id}	Update product details Yes
DELETE	/api/products/{id}	Delete a product	 Yes

### Order Endpoints:
Method	Endpoint	Description	 Auth Required
POST	/api/orders	Place a new order	 Yes
GET	/api/orders	Retrieve all orders	 Yes
GET	/api/orders/{id}	Get order by ID	 Yes
PUT	/api/orders/{id}	Update order details  Yes
DELETE	/api/orders/{id}	Cancel an order	 Yes

## Authentication & Security
Uses JWT (JSON Web Token) for authentication.
Include the Authorization: Bearer <token> in request headers.

### Example Login Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5..."
}

### Register a New User
POST http://localhost:8080/api/users/register 

         {
           "username": "john_doe",
           "email": "john@example.com",
           "password": "password123"
         }'

### Get All Products
GET http://localhost:8080/api/products

### Place an Order
POST http://localhost:8080/api/orders
'{
           "itemName": "Laptop",
           "category": "Electronics",
           "price": 1500,
           "seller": "TechStore",
           "buyer": "JohnDoe"
         }'

## Deployment
The API can be deployed using Docker, AWS, or Heroku.

## Run with Docker

## Features
User Management: Registration, login, authentication, and profile updates.
Product Management: Add, update, delete, and retrieve product details.
Order Processing: Create, view, update, and delete orders.
Security: JWT-based authentication and role-based access control.

## Key Tools & Technologies
 Backend: Spring Boot, Java, Maven
 Database: MySQL
 Security: Spring Security, JWT Authentication
 Testing: JUnit, Mockito, Postman
 Deployment: Docker, AWS/Heroku/Render
 Version Control: GitHub
 Project Management method:  Kanban - GitHub

## Conclusion
This API provides a scalable, secure, and efficient way to manage groceries online.
For any issues, create a GitHub Issue or reach out to the WeGroceries API team.

## Project strusture

src
├── main
│   ├── java
│   │   ├── com
│   │   │   ├── wegroceries
│   │   │   │   ├── WeGroceriesApplication.java       # Main application entry point
│   │   │   │   ├── authentication                    # Authentication feature
│   │   │   │   │   ├── AuthenticationRequest.java    # Authentication request model
│   │   │   │   │   ├── AuthenticationResponse.java   # Authentication response model
│   │   │   │   ├── config                            # Configuration classes
│   │   │   │   │   ├── SecurityConfig.java           # Security configuration
│   │   │   │   ├── common                            # Common utilities and error handling
│   │   │   │   ├── products                          # Product feature
│   │   │   │   │   ├── Product.java                  # Product model
│   │   │   │   │   ├── ProductController.java        # Product controller
│   │   │   │   │   ├── ProductService.java           # Product service
│   │   │   │   │   └── ProductRepository.java        # Product repository
│   │   │   │   ├── orders                            # Order feature
│   │   │   │   │   ├── Order.java                    # Order model
│   │   │   │   │   ├── OrderController.java          # Order controller
│   │   │   │   │   ├── OrderService.java             # Order service
│   │   │   │   │   └── OrderRepository.java          # Order repository
│   │   │   │   ├── users                             # User feature
│   │   │   │   │   ├── User.java                     # User model
│   │   │   │   │   ├── UserController.java           # User controller
│   │   │   │   │   ├── UserRepository.java           # User repository
│   │   │   │   │   ├── UserService.java              # User service logic
│   │   │   │   ├── exception                         # Global exceptions
│   │   │   │   ├── model                             # Additional models
│   │   │   │   │   └── User.java                     # User model (merged from the second structure)
│   │   │   │   ├── service                           # Service layer
│   │   │   │   │   └── CustomUserDetailsService.java # Custom user details service
│   │   │   │   └── util                              # Common utilities
│   ├── resources
│   │   ├── application.properties                    # Application properties (e.g., DB config, server)
└── pom.xml                                           # Maven build file with dependencies
