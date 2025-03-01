# wegrocerieapi

## Description
wegroceriesapi is a RESTful service designed to enable sellers, buyers, and delivery services to connect seamlessly for wholesale grocery transactions. It supports authentication, product management, order processing, and user management. The UserService module provides essential functionalities for user registration, retrieval, updating, and deletion. It ensures unique usernames and emails, maintains user records securely, and facilitates user identity validation. Built using Java and Spring Boot, this API adheres to a clean and modular structure for easy scalability, maintainability, and robust user management.

## Getting Started

## Project Management 
GitHub kanban board

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

## UserRoles Endpoints Roles added to the endpoints:

/api/admin/**: Only accessible by users with the ADMIN role.
/api/user/**: Accessible to both USER and ADMIN roles.
/api/manager/**: Accessible by users with the MANAGER role.
/api/support/**: Accessible by users with the SUPPORT role.
/api/vendor/**: Accessible by users with the VENDOR role.
/api/delivery/**: Accessible by users with the DELIVERY_PERSON role.
/api/analyst/**: Accessible by users with the ANALYST role.
/api/marketer/**: Accessible by users with the MARKETER role.
/api/developer/**: Accessible by users with the DEVELOPER role.
/api/customer/**: Accessible by users with the CUSTOMER role.

### Product Endpoints:

Endpoint	Allowed Roles
POST /api/products (Create Product)	ADMIN only
GET /api/products/{id} (Get Product by ID)	Everyone
GET /api/products (Get All Products)	Everyone
PUT /api/products/{id} (Update Product)	ADMIN only
DELETE /api/products/{id} (Delete Product)	ADMIN only
GET /api/products/exists (Check Product Existence)	Everyone

## Category Endpoint

Endpoint	Allowed Roles
POST /api/categories (Create Category)	ADMIN only
GET /api/categories (Get All Categories)	Everyone
GET /api/categories/{id} (Get Category by ID)	Everyone
DELETE /api/categories/{id} (Delete Category)	ADMIN only

### Order Endpoints:
Endpoint	Allowed Roles
POST /api/orders (Create Order)	ADMIN only
GET /api/orders/{id} (Get Order by ID)	Everyone
GET /api/orders (Get All Orders)	Everyone
PUT /api/orders/{id} (Update Order)	ADMIN only
DELETE /api/orders/{id} (Delete Order)	ADMIN only


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
Product Management: Add, update, delete, and retrieve product details.
Order Processing: Create, view, update, and delete orders.
User management: Create, view, update, and delete users.

## Features Roadmap
User Management: Registration, login, authentication.
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
│   │   │   │   ├── WeGroceriesApplication.java        # Main application entry point
│   │   │   │   ├── categories                         # Add this new package
│   │   │   │   │   ├── Category.java                  # Entity for categories
│   │   │   │   │   ├── CategoryController.java        # REST Controller
│   │   │   │   │   ├── CategoryRepository.java        # Repository for database access
│   │   │   │   │   ├── CategoryService.java           # Service layer for business logic
│   │   │   │   │   ├── CategoryDTO.java               # Data Transfer Object (Optional)
│   │   │   │   ├── Exception                          # Exception features
│   │   │   │   │   ├── GlobalExceptionHandler.java    # Custom Exception
│   │   │   │   │   ├── InvalidUserDataException.java  # Custom Exception
│   │   │   │   │   ├── OrderNotFoundException.java    # Custom Exception
│   │   │   │   │   ├── UserNameNotFoundException.java # Custom Exception
│   │   │   │   │   ├── UserNotFoundException.java     # Custom Exception
│   │   │   │   ├── orders                             # Order feature
│   │   │   │   │   ├── Order.java                     # Order entity
│   │   │   │   │   ├── OrderController.java           # Order controller
│   │   │   │   │   ├── OrderRepository.java           # Order Repository 
│   │   │   │   │   └── OrderService.java              # Order service
│   │   │   │   ├── products                           # Product feature
│   │   │   │   │   ├── Product.java                   # Product entity
│   │   │   │   │   ├── ProductController.java         # Product controller
│   │   │   │   │   └── ProductRepository.java         # Product repository
│   │   │   │   │   ├── ProductService.java            # Product service
│   │   │   │   ├── security                           # Security feature
│   │   │   │   │   ├── ApiSecurityConfig.java         # ApiSecurityConfig
│   │   │   │   │   ├── FilterConfig.java              # FilterConfig
│   │   │   │   │   ├── JwtTokenFilter.java            # JwtTokenFilter
│   │   │   │   │   ├── JwtTokenProvider.java          # JwtTokenProvider
│   │   │   │   │   ├── MyCustomFilter.java            # MyCustomFilter
│   │   │   │   │   ├── UserNamePasswordAuthenticationToken.java # Global exceptions
│   │   │   │   │   ├── WebSecurityConfigurerAdapter.java  # Custom User Details
│   │   │   │   ├── users                                  # User feature
│   │   │   │   │   ├── CustomUserDetailsService.java      # Custom User Details
│   │   │   │   │   ├── User.java                          # User entity
│   │   │   │   │   ├── UserController.java                # User controller
│   │   │   │   │   ├── UserRepository.java                # User repository
│   │   │   │   │   ├── UserService.java               # User service logic
│   │   │   │   ├── WegroceriesapiApplication.java     # Global exceptions
│   ├── resources
│   │   ├── application.properties                     # Application properties (e.g., DB config, server)
│   │   ├── local.properties
│   ├── test\java\com\wegroceries                      # Application test
│   │   ├── orders        
│   │   │   ├── OderControllerTest.java                # Application test
│   │   ├── products
│   │   │   ├── ProductControllerTest.java             # Application test
│   │   ├── users
│   │   │   ├── UserControllerTest.java                # Application test
│   │   ├── wegroceriesapi
│   │   │   ├── wegroceriesapiApplicationTest.java     # wegroceries Application class
├── pom.xml                                            # Maven build file with dependencies
└── README.md