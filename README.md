# AI Green Tick Notification Service

A Spring Boot microservice for handling notifications in the AI Green Tick ecosystem.

## Features

- RESTful API for notification management
- Email notification support
- H2 in-memory database for development
- Health check endpoints
- Actuator for monitoring
- Lombok for reducing boilerplate code

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Getting Started

### 1. Clone and Navigate to Project
```bash
cd aigreentickNotification
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

Or run the main class directly:
```bash
java -jar target/aigreentick-notification-0.0.1-SNAPSHOT.jar
```

### 4. Access the Application

- **Application**: http://localhost:8080/api/v1
- **Health Check**: http://localhost:8080/api/v1/notifications/health
- **Welcome**: http://localhost:8080/api/v1/notifications/welcome
- **H2 Console**: http://localhost:8080/api/v1/h2-console
- **Actuator Health**: http://localhost:8080/api/v1/actuator/health

## API Endpoints

### Health Check
- **GET** `/api/v1/notifications/health` - Check service health status

### Welcome
- **GET** `/api/v1/notifications/welcome` - Welcome message

## Configuration

### Database
The application uses H2 in-memory database by default. Configuration can be found in `src/main/resources/application.properties`.

### Email Configuration
To enable email notifications, configure the following properties:
```properties
spring.mail.host=your-smtp-host
spring.mail.port=587
spring.mail.username=your-email@example.com
spring.mail.password=your-app-password
```

## Project Structure

```
aigreentickNotification/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/aigreentick/notification/
│   │   │       ├── AigreentickNotificationApplication.java
│   │   │       └── controller/
│   │   │           └── NotificationController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/aigreentick/notification/
├── pom.xml
└── README.md
```

## Dependencies

- **Spring Boot Starter Web** - Web application framework
- **Spring Boot Starter Data JPA** - Database access
- **Spring Boot Starter Mail** - Email functionality
- **Spring Boot Starter Actuator** - Monitoring and management
- **Spring Boot Starter Validation** - Input validation
- **H2 Database** - In-memory database for development
- **Lombok** - Reduces boilerplate code

## Development

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package -Pprod
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.
