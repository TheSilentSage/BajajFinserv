# Webhook SQL Solver - Spring Boot Application

A Spring Boot application that solves SQL problems and submits solutions via webhook APIs.


## How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build and Run
```bash
# Build the application
mvn clean package

# Run the application
java -jar target/webhook-sql-solver-1.0.0.jar

# Or run with Maven
mvn spring-boot:run
```

## Project Structure

```
src/main/java/com/example/webhook/
├── WebhookSqlSolverApplication.java    # Main Spring Boot application
├── StartupRunner.java                  # ApplicationRunner for webhook flow
└── RestTemplateConfig.java            # RestTemplate configuration
```


## Download Links

- **JAR File**: `target/webhook-sql-solver-1.0.0.jar`
- **Source Code**: Complete Maven project structure
- **Raw JAR**: Available in the `target/` directory after building

## License

This project is created for the webhook SQL challenge submission.
