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