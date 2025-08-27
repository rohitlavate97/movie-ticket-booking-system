# Movie Ticket Booking System

A microservices-based system built with Spring Boot and Spring Cloud for booking movie tickets. It demonstrates service discovery, API gateway routing, inter-service communication, database persistence, and payment integration.

## Architecture
- `movie-service-registry` (Eureka Server): Service discovery.
- `api-gateway` (Spring Cloud Gateway): Single entrypoint, routing, circuit breaker.
- `booking-service`: Creates and persists bookings, calls payment-service via OpenFeign.
- `payment-service`: Processes payments (Stripe SDK) and persists payment records.
- `notification-service`: Placeholder for async notifications.
- `commons`: Shared DTOs and constants library.

## Tech Stack
- Java 17, Spring Boot 3.4.x, Spring Cloud 2024.0.x
- Spring Cloud Gateway, Netflix Eureka, OpenFeign, JPA/Hibernate
- MySQL 8.x
- Stripe Java SDK
- Docker, Docker Compose
- Maven

## Repository layout
```
api-gateway/
booking-service/
commons/
docker-compose.yml
movie-service-registry/
notification-service/
payment-service/
pom.xml (parent)
```

## Prerequisites
- JDK 17+
- Maven 3.9+
- MySQL 8.x (local) or Docker (recommended)
- Stripe test key (for payment-service; optional for local dev)

## Quick start
### 1) Build
- Full build (skipping DB-dependent tests):
```bash
mvn clean install -DskipTests
```

### 2) Run with Docker Compose (recommended)
The current `docker-compose.yml` starts: eureka-server, api-gateway, booking-service, payment-service, notification-service. You should add a MySQL service (or run MySQL locally). Example MySQL service to append to your compose file:
```yaml
services:
  mysql:
    image: mysql:8.0
    container_name: mysql
    ports:
      - "3306:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=secret
    volumes:
      - mysql-data:/var/lib/mysql
      - ./mysql-init:/docker-entrypoint-initdb.d
volumes:
  mysql-data:
```
Create init SQL so both DBs exist:
```
mkdir -p mysql-init
cat > mysql-init/01-init.sql <<'SQL'
CREATE DATABASE IF NOT EXISTS bookings;
CREATE DATABASE IF NOT EXISTS payments;
SQL
```
Update environment for services in `docker-compose.yml` (if not present):
```yaml
  booking-service:
    environment:
      - MYSQL_HOST=mysql
      - MYSQL_PORT=3306
      - MYSQL_DB=bookings
      - MYSQL_USER=root
      - MYSQL_PASSWORD=secret

  payment-service:
    environment:
      - MYSQL_HOST=mysql
      - MYSQL_PORT=3306
      - MYSQL_DB=payments
      - MYSQL_USER=root
      - MYSQL_PASSWORD=secret
```
Then start:
```bash
docker compose up -d --build
```

### 3) Run locally (without Docker)
1) Start MySQL and create DBs and an app user:
```sql
CREATE DATABASE IF NOT EXISTS bookings;
CREATE DATABASE IF NOT EXISTS payments;

CREATE USER 'appuser'@'%' IDENTIFIED BY 'strongpass';
GRANT ALL PRIVILEGES ON bookings.* TO 'appuser'@'%';
GRANT ALL PRIVILEGES ON payments.* TO 'appuser'@'%';
FLUSH PRIVILEGES;
```
2) Configure services by updating `application.yaml` or via env vars
- booking-service:
```
spring.datasource.url=jdbc:mysql://localhost:3306/bookings?createDatabaseIfNotExist=true
spring.datasource.username=appuser
spring.datasource.password=strongpass
```
- payment-service:
```
spring.datasource.url=jdbc:mysql://localhost:3306/payments?createDatabaseIfNotExist=true
spring.datasource.username=appuser
spring.datasource.password=strongpass
```
3) Start services (in order):
```bash
# each in its own terminal
cd movie-service-registry && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
cd payment-service && mvn spring-boot:run
cd booking-service && mvn spring-boot:run
cd notification-service && mvn spring-boot:run
```

## Configuration
### Common ports
- Eureka: 8761
- API Gateway: 8765
- Booking Service: 8080
- Payment Service: 9090
- Notification Service: 7070

### Environment variables (used by services)
- `SERVER_PORT`, `APPLICATION_NAME`
- `REGISTER_WITH_EUREKA`, `DEFAULT_ZONE`
- `MYSQL_HOST`, `MYSQL_PORT`, `MYSQL_DB`, `MYSQL_USER`, `MYSQL_PASSWORD`
- payment-service: `stripe.key` (Stripe secret key)

## API
- Booking Service
  - POST `/bookings` → create a booking (calls payment-service). Body is `BookingDTO`.
- Payment Service
  - GET `/payments` → health/test endpoint
  - POST `/payments` → processes payment for a `BookingDTO`

## Development
- Skipping tests when DB is not available:
```bash
mvn clean install -DskipTests
```
- Optional: use an in-memory DB for tests (H2) via a `test` profile.

## Troubleshooting
- Build fails in `booking-service` tests with Hibernate dialect error → start MySQL, configure datasource, or run `-DskipTests`.
- Access denied for MySQL root → create an application user (`appuser/strongpass`) and use that in the services.
- Gateway fallback showing maintenance → ensure dependent services are registered and healthy in Eureka.

