# ImmobilienScout 24 Technical Challenge

The objective is to build a web application that allows a user to conduct some analysis of the HTML web page.

### Technology Stack
* Java 8
* Spring Boot
* Jsoup
* Guava
* Mockito / RestAssured
* Jquery
* Twitter Bootstrap
* Maven

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

### Run service
To run the application please use one the the commands below:
```
mvn package spring-boot:run
```
or
```
java -jar target/html-analysis-service-0.0.1-SNAPSHOT.jar
```
### Application design
Application divided into 2 parts: back-end and front-end

#### Back-end
Application back-end implemented as REST web service, which provides only 1 resource, to submit URL for analyse.

<b>Implementation highlights</b>
1. To check is web page contains login form or not, I selected all input elements with type password.
 Then check if there is at least one element found, I think that the page contains login form. There is at least one 'gotcha' with this approach - 
 registration pages also has password fields (even 2). Also there some site that has field on the each page while used in not logged in.
2. To check HTML version I parse DOCTYPE element.
3. To check is links on the web page lead to the site with same domain name or not Google Guava library was used.
4. Checking each link on the page done in parallel with the help of ThreadPoolTaskExecutor. ThreadPoolTaskExecutor has separated configuration,
all general settings(e.g core pool size, max pool size, queue size) can be set via application.yml file (or as application start params).
Async method return Future object with responce HTTP status code the submitted URL.

All html parsing logic covered with unit and integration tests.

Back-end logic can be verified via curl like:
```
curl -X POST http://localhost:8080/analyze -H 'content-type: application/json' -d '{"url": "https://www.mkyong.com/spring-boot/spring-boot-ajax-example"}'
```
 

#### Front-end
Front-end is very simple HTML page with 1 input field to enter URL for analyse and button to submit request to the service. It's done via Ajax.
After HTML page analyse is done, 3 tables appeats on the page. 
1. Contains general information about Web page according to requirements
2. Contains information about Headings elements statistics
3. Contains information about all collected links from the Web page (address, type[INTERNAL/EXTERNAL], HTTP response status code)
Submitting request to the server, parsing reponse and populating html tables done with help of Jquery library. UI build with Twitter Bootstrap.

Front-end side can be reached http://localhost:8080

### Environment
macOS Sierra (version 10.12.4)
Java(TM) SE Runtime Environment (build 1.8.0_66-b17)
