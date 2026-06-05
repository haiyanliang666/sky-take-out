# Sky Take Out

Sky Take Out is a Spring Boot based food delivery backend system. It provides REST APIs for both the merchant/admin management platform and the customer mini-program side, covering employee management, categories, dishes, set meals, shopping carts, orders, reports, shop status, WebSocket notifications, file upload, and payment callback handling.

This project is from the "Cang Qiong Take Out" enterprise Java practice course and is organized as a Maven multi-module application.

## Features

- Admin login and JWT authentication
- Employee account management
- Dish category management
- Dish and flavor management
- Set meal management
- Shop open/closed status control
- Customer login
- Customer address book management
- Shopping cart add, subtract, list, and clean operations
- Order submission, payment flow, cancellation, repeat order, and reminder APIs
- Admin order search, confirmation, rejection, delivery, completion, and cancellation
- Business dashboard and report APIs
- Excel export for operation reports
- WebSocket order reminder/notification support
- Redis caching support
- Aliyun OSS file upload integration
- WeChat Pay utility and payment notification endpoints
- Knife4j API documentation

## Tech Stack

- Java
- Spring Boot 2.7.3
- Spring MVC
- Spring Data Redis
- Spring Cache
- Spring WebSocket
- MyBatis
- MySQL
- Druid connection pool
- PageHelper
- JWT
- Lombok
- Knife4j / Swagger 2
- Apache POI
- Aliyun OSS SDK
- WeChat Pay API v3 client

## Project Structure

```text
sky-take-out
├── pom.xml                 # Parent Maven project
├── sky-common              # Shared constants, utilities, exceptions, results, properties
├── sky-pojo                # Entity, DTO, and VO classes
└── sky-server              # Spring Boot application, controllers, services, mappers, config
```

## Main API Areas

### Admin APIs

- `POST /admin/employee/login` - admin login
- `/admin/employee/**` - employee management
- `/admin/category/**` - category management
- `/admin/dish/**` - dish management
- `/admin/setmeal/**` - set meal management
- `/admin/order/**` - order management
- `/admin/report/**` - business reports and export
- `/admin/workspace/**` - dashboard overview data
- `/admin/shop/**` - shop status
- `/admin/common/upload` - file upload

### User APIs

- `POST /user/user/login` - customer login
- `/user/category/**` - category list
- `/user/dish/**` - dish list
- `/user/setmeal/**` - set meal list and details
- `/user/addressBook/**` - address book management
- `/user/shoppingCart/**` - shopping cart operations
- `/user/order/**` - order submission, history, detail, payment, cancel, repeat order
- `/user/shop/status` - shop status

## Requirements

- JDK 8 or later
- Maven 3.6+
- MySQL 5.7+ or 8.x
- Redis

## Configuration

Runtime configuration is defined in:

- `sky-server/src/main/resources/application.yml`
- `sky-server/src/main/resources/application-dev.yml`

Before running the application, update `application-dev.yml` for your local environment:

```yaml
sky:
  datasource:
    host: localhost
    port: 3306
    database: sky_take_out
    username: your_mysql_username
    password: your_mysql_password
  redis:
    host: localhost
    port: 6379
    password: your_redis_password
    database: 10
  alioss:
    endpoint: your_oss_endpoint
    access-key-id: your_access_key_id
    access-key-secret: your_access_key_secret
    bucket-name: your_bucket_name
  wechat:
    appid: your_wechat_appid
    secret: your_wechat_secret
```

> Do not commit real database passwords, OSS keys, WeChat secrets, payment certificates, or private keys to a public GitHub repository.

## Database

Create a MySQL database named `sky_take_out`:

```sql
CREATE DATABASE sky_take_out DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

Then import the course/project SQL schema and seed data before starting the backend.

## Run Locally

From the project root:

```bash
cd sky-take-out
mvn clean package
```

Start the server:

```bash
mvn -pl sky-server spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## API Documentation

After the server starts, open Knife4j documentation:

```text
http://localhost:8080/doc.html
```

The documentation is grouped into:

- Management/admin APIs
- User/customer APIs

## Tests

Run all Maven tests:

```bash
mvn test
```

Run tests for the server module:

```bash
mvn -pl sky-server test
```

## Notes

- Admin APIs require the `token` request header after login.
- User APIs require the `authentication` request header after login.
- Redis is used by cache-related functionality and tests.
- WeChat Pay, Aliyun OSS, and Baidu map keys must be configured with valid credentials before those integrations can be used in production.
- The project currently contains backend code only. Admin frontend and mini-program frontend clients should call the exposed REST APIs.

## License

This repository is intended for learning and practice. Add a license file if you plan to publish or reuse it beyond coursework.
