# Full stack application with Kotlin and React.js

A full-stack application that provides a CRUD API for creating Articles, Comments and Users. 
The application is built with: 

- Kotlin
- Spring Boot
- H2 Database at the moment with the intention of switching later to the PostgreSQL database
- React.js for the frontend (not yet in use because the focus is on BE)

The application is built with the intention of practicing building complex systems and learning new technologies.

# How to run the project

1. Clone the repository
2. Build the project with the following command: `mvn clean install -DskipTests`
3. Run the backend with the following command: `mvn spring-boot:run`
4. Open the browser and navigate to `http://localhost:8181`
5. You can now interact with the application's backend application


## Some of the API endpoints
- `http://localhost:8181/api/v1/articles?pageNo=0&pageSize=10&sortBy=id&sortDir=asc` - Get all articles

- `http://localhost:8181/api/v1/comments/article/1` - Get all comments for the article with id 1

- `http://localhost:8181/api/v1/users/1` - Get user with id 1

For better API documentation, I plan to introduce Swagger later.
