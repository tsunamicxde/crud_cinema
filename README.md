# CRUD REST API for Online Cinema

<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="spring logo"  />
  <img width="12" />
  <img src="https://skillicons.dev/icons?i=hibernate" height="40" alt="hibernate logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" height="40" alt="postgresql logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" height="40" alt="docker logo"  />
</div>

###

This project is a CRUD REST API for managing content in an online cinema, developed using Spring Boot. The API supports create, read, update, and delete operations for various entities: movies, reviews, critics, directors, actors, and genres.

### Entities:

- **Movies**: Create, read, update, and delete operations for movie information such as title, description, genres, duration, release year, director, actors, and rating.
- **Genres**: Create, read, update, and delete operations for genres under which movies are classified.
- **Actors and Directors**: Create, read, update, and delete operations for actors and directors related to movies.
- **Reviews**: Create, read, update, and delete operations for reviews on movies with ratings and text.
- **Reviewers**: Create, read, update, and delete operations for reviewers who can leave feedback on movies.


## Installation

### Requirements

- Java 21 or higher
- Maven
- PostgreSQL
- Spring Boot 3.3.4
- Spring Data JPA


### Cloning the Repository

Clone this repository to your local computer:

```bash
https://github.com/tsunamicxde/crud_cinema.git
```

## Database Setup

Configure the database connection parameters in the **application.properties** file:

```bash
spring.application.name=crud_cinema
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
spring.datasource.username=your_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## Docker Setup

### Build and Run with Docker

To build and run the application using Docker, follow these steps:

1. Create a Dockerfile in the root of your project:

```dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/crud_cinema-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_APPLICATION_NAME=crud_cinema
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/your_db_name
ENV SPRING_DATASOURCE_USERNAME=your_username
ENV SPRING_DATASOURCE_PASSWORD=your_db_password
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect

ENTRYPOINT ["java", "-jar", "app.jar"]
```

2. Create a docker-compose.yml file in the root of your project:

```yml
services:
  db:
    image: postgres:latest
    environment:
      POSTGRES_DB: your_db_name
      POSTGRES_USER: your_username
      POSTGRES_PASSWORD: your_db_password
    ports:
      - "5432:5432"

  app:
    build: .
    depends_on:
      - db
    ports:
      - "8080:8080"
    environment:
      SPRING_APPLICATION_NAME: crud_cinema
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/your_db_name
      SPRING_DATASOURCE_USERNAME: your_username
      SPRING_DATASOURCE_PASSWORD: your_db_password
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
      SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT: org.hibernate.dialect.PostgreSQLDialect
```

3. Build and run the application with Docker Compose

Make sure you are in the root directory of your project and run:

```bash
docker-compose up --build
```

## Running the Application
1. Navigate to the root directory of the project.
2. Use Maven to build and run the application:

```bash
mvn clean spring-boot:run
```

## Using the API

### Movies

#### Get All Movies

```bash
GET /api/movies
```

#### Get Movie by ID

```bash
GET /api/movies/{id}
```

#### Create a New Movie

```bash
POST /api/movies
```

#### Request Body

```json
{
    "name": "The Hateful Eight",
    "description": "A tense Western thriller by Quentin Tarantino. Eight strangers find themselves trapped in a cabin after the Civil War in the U.S. Forced proximity breeds suspicion, and tension escalates into a brutal confrontation.",
    "duration": 187,
    "genres": [
        { "id": 1 },
        { "id": 2 },
        { "id": 3 }
    ],
    "year": 2015,
    "director": { "id": 1 },
    "actors": [
        { "id": 1 },
        { "id": 2 },
        { "id": 3 }
    ]
}
```

#### Update Movie Information

```bash
PUT /api/movies/{id}
```

#### Request Body

```json
{
    "name": "The Hateful Eight",
    "description": "A tense Western thriller by Quentin Tarantino. Eight strangers find themselves trapped in a cabin after the Civil War in the U.S. Forced proximity breeds suspicion, and tension escalates into a brutal confrontation.",
    "duration": 187,
    "genres": [
        { "id": 1 },
        { "id": 2 },
        { "id": 3 },
        { "id": 4 }
    ],
    "year": 2015,
    "director": { "id": 1 },
    "actors": [
        { "id": 1 },
        { "id": 2 },
        { "id": 3 }
    ]
}
```

#### Delete Movie

```bash
DELETE /api/movies/{id}
```

### Genres

#### Get All Genres

```bash
GET /api/genres
```

#### Get Genre by ID

```bash
GET /api/genres/{id}
```

#### Create a New Genre

```bash
POST /api/genres
```

#### Request Body

```json
{
    "name": "Western",
    "description": "..."
}
```

#### Update Genre Information

```bash
PUT /api/genres/{id}
```

#### Request Body

```json
{
    "name": "Western",
    "description": "Films that immerse viewers in the atmosphere of the Wild West with its cowboys, shootouts, and the struggle between good and evil. Captivating stories of survival, honor, and freedom."
}
```

#### Delete Genre

```bash
DELETE /api/genres/{id}
```

### Actors

#### Get All Actors

```bash
GET /api/actors
```

#### Get Actor by ID

```bash
GET /api/actors/{id}
```

#### Create a New Actor

```bash
POST /api/actors
```

#### Request Body

```json
{
    "name": "Samuel",
    "surname": "Jackson",
    "birthDate": "1948-12-21",
    "birthPlace": "USA"
}
```

#### Update Actor Information

```bash
PUT /api/actors/{id}
```

#### Request Body

```json
{
    "name": "Samuel",
    "surname": "Jackson",
    "birthDate": "1948-12-21",
    "birthPlace": "Washington, USA"
}
```

#### Delete Actor

```bash
DELETE /api/actors/{id}
```

### Directors

#### Get All Directors

```bash
GET /api/directors
```

#### Get Director by ID

```bash
GET /api/directors/{id}
```

#### Create a New Director

```bash
POST /api/directors
```

#### Request Body

```json
{
    "name": "Quentin",
    "surname": "Tarantino",
    "birthDate": "1963-03-27",
    "birthPlace": "USA"
}
```

#### Update Director Information

```bash
PUT /api/directors/{id}
```

#### Request Body

```json
{
    "name": "Quentin",
    "surname": "Tarantino",
    "birthDate": "1963-03-27",
    "birthPlace": "Knoxville, Tennessee, USA"
}
```

#### Delete Director

```bash
DELETE /api/directors/{id}
```

### Reviewers

#### Get All Reviewers

```bash
GET /api/reviewers
```

#### Get Reviewer by ID

```bash
GET /api/reviewers/{id}
```

#### Create a New Reviewer

```bash
POST /api/reviewers
```

#### Request Body

```json
{
    "name": "Petr",
    "surname": "Shevtsov"
}
```

#### Update Reviewer Information

```bash
PUT /api/reviewers/{id}
```

#### Request Body

```json
{
    "name": "Petr",
    "surname": "Jackson"
}
```

#### Delete Reviewer

```bash
DELETE /api/reviewers/{id}
```

### Reviews

#### Get All Reviews

```bash
GET /api/reviews
```

#### Get Review by ID

```bash
GET /api/reviews/{id}
```

#### Create a New Review

```bash
POST /api/reviews
```

#### Request Body

```json
{
    "message": "Masterfully shot, captivating until the end.",
    "rating": 9.0,
    "reviewer": { "id": 1 },
    "movie": { "id": 1 }
}
```

#### Update Review Information

```bash
PUT /api/reviews/{id}
```

#### Request Body

```json
{
    "message": "Masterfully shot, captivating until the end.",
    "rating": 10.0,
    "reviewer": { "id": 1 },
    "movie": { "id": 1 }
}
```

#### Delete Review

```bash
DELETE /api/reviews/{id}
```

## Developers

- [tsunamicxde](https://github.com/tsunamicxde)
