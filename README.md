## Approach and Assumption

* REST API Development: Implements a RESTful API for your application.
* OpenAPI Documentation: Integrates OpenAPI documentation for API exploration.
* Spring Boot Actuator: Exposes health and monitoring endpoints for application management.
* Configurable Properties: Leverages properties files (e.g., config.properties) to configure:
* Request Timeout: Set the maximum time (in milliseconds) a REST client waits for a response before timing out.
* Connection Pool Size: Control the number of concurrent connections the REST client can maintain.
* Popularity Score Threshold: Define the minimum score required for a repository to be considered popular.
* REST Client Configuration: Utilizes RestTemplate as the REST client with configurable timeout and connection pool size.
* Secure Access Token: Stores the access token for authentication in a properties file and sends it as an "Authorization" header with REST client requests.
* Production Readiness:
Dockerfile: Provides a Dockerfile for containerization, enabling easy deployment and scaling.
Docker Compose (Optional): Includes instructions on using a Docker Compose file to run your service alongside other services in a multi-container environment.


## Prerequisites

* Docker 19.03.x (for production level readiness)
* Docker Compose 1.25.x
* Maven 3

## Used Technologies
* Spring Boot 3
* Lombok
* Actuator

## How to run

### Package application as a JAR file

```sh
mvn clean install -DskipTests
```

### Package application as a JAR file

```sh
mvn test
```

### Run with docker compose

```sh
docker-compose up -d
```

### Run as Java -jar

```sh
java -jar target/githubrepo-0.0.1-SNAPSHOT.jar
```

### Health status
http://localhost:8080/actuator/health

### Open api docs
http://localhost:8080/swagger-ui/index.html