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
If everything is working correctly, the output will appear "stuck" and the command prompt won't return until you stop the application, which should now be running at http://localhost:8080/api/ious.

### Stop Application
Stop the application by pressing `Ctrl + C`

