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

### Run Kafka with Zookeeper and Postgresql

```sh
docker-compose up -d
```

### Run Spring Boot application

```sh
mvn spring-boot:run
```

Or, run as Java -jar

```sh
java -jar target/githubrepo-0.0.1-SNAPSHOT.jar
```