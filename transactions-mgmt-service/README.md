# Transaction management system

## Description
Service implementation providing REST API for transactions statistics calculation

## Technology Stack
* Java 8
* Spring Boot
* Maven
* Docker

## Implementation Details

### Assumptions
* If there are no transaction for last 60 seconds, response will contains 0 as value for all fields
* For saving transaction requests only timestamp field value validation is implemented (should be not older that 60 seconds). 
Application logic expects that request will contain valid values in amount and timestamp fields. 

### Complexity
Time and memory complexity of both API endpoint is O(1)

### Further improvements
* Make TIME_LIMIT constant configurable

### General algorithm
* ConcurrentHashMap used as in-memory storage of statistics. It can contains max 60 entries.
* Maximum number of entries keept with eviction logic, which evict entries older then 60 seconds from the map  
* Each valid incoming transaction grouped by it's timestamp and it's value applied to appropriate statistic record.
* When get statistics endpoint is call statistics summary is calculated from all map entries. 
 

## Run application
To run the application please use one the the commands below:
```
mvn spring-boot:run
```
or
```
java -jar target/transactions-mgmt-service-0.0.1-SNAPSHOT.jar

```
or 
```
docker run -p 8081:8081 -t karmanov/transactions-mgmt-service
```

## CURL commands

### Submit transaction
* **Description:** Stores transaction for processing
* **URL**
`/v1/transactions`
* **Method**
`POST`
* **Request body**
```
{
	"amount": 15.87,
	"timestamp": 1503533948
}
```
#### **Success Response**
* **Condition:** If transaction timestamp is not older then configured shift(default value 60 seconds) <br>
* **Code** `201 CREATED`
#### **Error Responses**
* **Condition:** If transaction timestamp is older then configured shift(default value 60 seconds) <br>
* **Code** `204 NO_CONTENT`

#### **Example**
```
curl -X POST http://localhost:8081/v1/transactions -H 'content-type: application/json' -d '{"amount": 15.87,"timestamp": 1503533948}'

```

### Statistics
* **Description:** Provides transactions statistics for last 60 seconds

#### **Success Response**
* **Code** `200 OK`
* **Reponse Body**
```
{
    "sum": 31.74,
    "avg": 15.87,
    "max": 15.87,
    "min": 15.87,
    "count": 2
}
```

#### **Example**
```
curl -X GET http://localhost:8081/v1/statistics
```

## Swagger documentation UI
Swagger UI documentation could be reachable via the [link](http://localhost:8081/swagger-ui.html)

## Environment
macOS Sierra (version 10.12.4)
Java(TM) SE Runtime Environment (build 1.8.0_66-b17)