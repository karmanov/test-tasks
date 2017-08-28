### Technology Stack
* Java 8;
* Spring Boot

### Build application
Apache Maven is used as build tool for the service. Also it uses Maven Wrapper, so application can be build event on 'clean' system.
To build the project please use one of the commands below from service root directory:

```
mvn clean package
```

### Run service
To run the application please use one the the commands below:
```
mvn spring-boot:run
```

```
java -jar target/challenge-0.0.1-SNAPSHOT.jar
```