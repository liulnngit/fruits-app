# FRUITS APPLICATION
Information about fruits nutrition with emojis

## Technical stack
- JDK 11
- Spring Boot 2.6.6
- Spring Cloud 2021.0.1 (Eureka, API Gateway)
- Spring Data JPA
- Spring Security
- PostgresSql, MySql
- Swagger //does not work :(
- Spring Cloud Sleuth
- Zipkin
- Maven
- Docker

## Microservices
- discovery-service - holds the information about all services
- api-gateway - routs requests
- emoji-service - responsible for emojis
- fruits-service - consumes fruits from https://www.fruityvice.com/ and enriches fruits information with emojis
- user-service - responsible for users management and authorization
- config-service //todo

## How to run
To start a project run following commands in a project root directory:
1. `mvn clean install` to build project
2. `docker-compose build` to build containers
3. `docker-compose up -d` create and start the containers in the background and leave them running