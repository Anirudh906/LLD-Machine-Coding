# URL Shortener Service

A Spring Boot application that shortens URLs using SHA-256 hash function and Base62 encoding scheme, with PostgreSQL database integration.

## Features

- **URL Shortening**: Converts long URLs to short 7-character Base62 encoded strings
- **SHA-256 Hashing**: Uses SHA-256 hash function for URL encoding
- **Base62 Encoding**: Encodes hash values using Base62 (0-9, A-Z, a-z)
- **PostgreSQL Integration**: Stores URLs in PostgreSQL database
- **URL Redirection**: Retrieves original URLs from short URLs and redirects

## Technology Stack

- Java 17
- Spring Boot 4.0.0
- Spring Data JPA
- PostgreSQL
- Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+ installed and running

## Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE urlshortener;
```

2. Update `application.properties` with your PostgreSQL credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortener
spring.datasource.username=postgres
spring.datasource.password=your_password
```

The application will automatically create the `urls` table on startup using JPA's `ddl-auto=update`.

## Running the Application

1. Build the project:
```bash
mvn clean install
```

2. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### 1. Shorten URL
**POST** `/api/v1/url/shorten`

Request body:
```json
{
  "url": "https://www.example.com/very/long/url/path"
}
```

Response:
```json
{
  "originalUrl": "https://www.example.com/very/long/url/path",
  "shortUrl": "aBc1234"
}
```

### 2. Redirect to Original URL
**GET** `/api/v1/url/{shortUrl}`

Example: `GET /api/v1/url/aBc1234`

This will redirect to the original URL if found, or return 404 if not found or expired.

## How It Works

1. **URL Shortening Process**:
   - Takes the original URL
   - Generates SHA-256 hash
   - Converts hash to Base62 encoding (7 characters)
   - Checks for collisions and handles them
   - Stores in database

2. **URL Retrieval Process**:
   - Takes the short URL
   - Searches database for matching record
   - Validates expiration date
   - Returns original URL for redirection

## Database Schema

The `urls` table contains:
- `id`: Primary key (auto-generated)
- `original_url`: The original long URL
- `short_url`: The shortened URL (unique, 7 characters)
- `created_at`: Timestamp when URL was created
- `expires_at`: Expiration timestamp (default: 1 year from creation)

## Project Structure

```
src/main/java/com/systemdesign/urlshortener/
├── controllers/
│   ├── UrlController.java          # REST endpoints
│   └── GlobalExceptionHandler.java # Exception handling
├── dto/
│   ├── UrlRequest.java             # Request DTO
│   └── UrlResponse.java            # Response DTO
├── model/
│   └── UrlEntity.java              # JPA entity
├── repository/
│   └── UrlRepository.java          # JPA repository
├── service/
│   ├── UrlService.java             # Service interface
│   └── impl/
│       └── UrlServiceImpl.java     # Service implementation
└── util/
    └── Base62Encoder.java          # SHA-256 + Base62 encoding utility
```

## Testing

You can test the API using curl:

```bash
# Shorten a URL
curl -X POST http://localhost:8080/api/v1/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://www.google.com"}'

# Access shortened URL (will redirect)
curl -L http://localhost:8080/api/v1/url/{shortUrl}
```

## Notes

- Short URLs are 7 characters long
- Default expiration is 1 year from creation
- Collision handling: if a short URL already exists, a counter is appended
- The application uses JPA's `ddl-auto=update` to automatically create/update the database schema
