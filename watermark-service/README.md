# Watermark Service

### Description
The goal of the technical challenge is to create asynchronous web service for watermarking documents of different types. 

### Technology Stack
* Java 8;
* Spring Boot
* Spring Data JPA
* Spring Web
* REST
* H2

### Implementation Details

### Build application
Apache Maven is used as build tool for the service. Also it uses Maven Wrapper, so application can be build event on 'clean' system.
To build the project please use one of the commands below from service root directory:
```
./mvnw clean package
```
or
```
./mvnw.cmd clean package
```
or
```
mvn clean package
```

or build docker image
```
mvn clean docker:build
```

### Run service
To run the application please use one the the commands below:
```
mvn spring-boot:run -Dspring.profiles.active=demo
```
or
```
java -jar -Dspring.profiles.active=demo target/html-analysis-service-0.0.1-SNAPSHOT.jar
```
or 
```
docker run -e "SPRING_PROFILES_ACTIVE=demo" -p 8081:8081 -t karmanov/watermark-service
```

Please note, than in each example <i>demo</i> profile is used as active. This is done to create some test data in the db.

### Curl commands
CURLs:
```
curl -X GET http://localhost:8081/watermark-service/documents
curl -X POST http://localhost:8081/watermark-service/watermark -H 'content-type: application/json' -d '{"documentId": 2}'
curl -X GET http://localhost:8081/watermark-service/tickets/d548227a-ed91-49f8-baef-af8d8e1c9f1a
curl -X GET http://localhost:8081/watermark-service/documents?page=0&size=3&sort=author,desc
```

### Swagger documentation UI
Swagger UI documentation could be reachable via the link: http://localhost:8081/swagger-ui.html

### H2 database console
<b>Link</b>: http://localhost:8081/h2-console/

<b>JDBC URL</b>: jdbc:h2:mem:watermark

<b>User Name</b>: sa

<b>Password</b>:  

### Implementation details

#### Asynchronous processing
According to requirements watermark-service implemented in asynchronous way. After receiving watermarking request, 
it check if required document exist in database and there is not pending ticket for watermarking this document.
If all validation passed it creates new ticket and delegate the job of watermaking to separate thread. It is achieved with help 
of thread pool task executor. Watermarking simulation delay configurable from application.yml file.
Thread pool task executor also configurable from application.yml (number of core/max thread and queue capacity)

#### Object model
I choose "Single Table" mapping strategy for this scenario.
For watermarking there 1 processor per document type. 

### Possible improvements:
* Add batch endpoint, to request watermarking documents in batches.
* Add new type of tests (Load test (e.g. with help of gatling framework), BDD (with help of Cucumber framework))

### Environment
macOS Sierra (version 10.12.4)
Java(TM) SE Runtime Environment (build 1.8.0_66-b17)