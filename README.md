# CRUD REST API Кинотеатр
![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-green?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14.10-blue?style=for-the-badge&logo=postgresql)

Этот проект представляет собой CRUD REST API для управления контентом онлайн-кинотеатра, разработанный с использованием Spring Boot. Позволяет выполнять операции создания, чтения, обновления и удаления фильмов.

## Установка

### Требования

- Java 21 или выше
- Maven
- PostgreSQL (или другой совместимый с JDBC сервер базы данных)

### Клонирование репозитория

Склонируйте этот репозиторий:

```bash
git clone https://github.com/tsunamicxde/crud_cinema.git
```

## Настройка базы данных
1. Создайте базу данных в PostgreSQL:
```bash
    CREATE DATABASE cinema_db;
```
2. Создайте таблицу movies в базе данных:
```bash
    CREATE TABLE movies (
        id SERIAL PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        genre VARCHAR(100),
        year INT
    );
```
3. Настройте параметры подключения к базе данных в файле application.properties:
```bash
    spring.datasource.url=jdbc:postgresql://localhost:5432/cinema_db
    spring.datasource.username=ваш_пользователь
    spring.datasource.password=ваш_пароль
    spring.jpa.hibernate.ddl-auto=update
```

## Запуск приложения

1. Перейдите в корневую директорию проекта
2. Используйте Maven для сборки и запуска приложения:
```bash
    mvn clean spring-boot:run
```

## Использование API
### Получение всех фильмов

```bash
    GET /api/movies
```

### Получение фильма по id

```bash
    GET /api/movies/{id}
```

### Создание нового фильма

```bash
    POST /api/movies
```

#### Пример запроса

```json
{
    "title": "Омерзительная восьмёрка",
    "genre": "Триллер",
    "year": 2015
}
```
#### Ответ

```
    Movie created successfully
```

### Обновление фильма

```
    PUT /api/movies/{id}
```

#### Пример запроса

```json
{
    "title": "Омерзительная восьмёрка",
    "genre": "Вестерн",
    "year": 2015
}
```

#### Ответ

```
    Movie updated successfully
```

### Удаление фильма

```bash
    DELETE /api/movies/{id}
```

#### Ответ

```
    Movie deleted successfully
```
