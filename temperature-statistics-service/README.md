# Temperature sensors statistics service

## Description
 HTTP   webservice   that   receives   measurements   from temperature   sensors,   stores   and   exposes   information   about   these   sensors.
 
## Technology Stack
* Java 8
* Spring Boot
* Spring Data JPA
* H2 database
* Maven
* Docker

### Build application
Apache Maven is used as build tool for the service. To build the project please use one of the commands below from service root directory:
```
mvn clean package
```

or build docker image
```
mvn install dockerfile:build
```

## Run application
To run the application please use one the the commands below:
```
mvn spring-boot:run
```
or
```
java -jar target/temperature-statistics-service-0.0.1-SNAPSHOT.jar

```
or 
```
docker run -p 9092:9092 -t karmanov/temperature-statistics-service:latest
```

## Assumptions:
* Endpoint /events designed to respond HTTP Status Code 200 and payload with sensorId and empty list of events if no 
events found 
* Endpoint /statistics designed to respond HTTP Status Code 200 and payload with sensorId and 0.0 as fallback values for 
statistics fields if no measurement found for specified sensor id
* when TEMPERATURE_EXCEEDED event triggered for sensor, value of last (3rd) measurement recorded as event's temperature value

## API

### Submit measurement
* **Description:** Add new measurement for provided sensor id
* **URL**
`/temperature-statistics/api/v1/sensors/{uuid}/measurement`
* **Method**
`POST`
* **Request body**
```
{
	"temperature": 15.87
}
```
#### **Success Response**
* **Condition:** If sensor id and temperature values are valid <br>
* **Code** `201 CREATED`
* **Payload**:
```
{
    "id": "38a310fe-6f94-457b-b0d6-6268a19082a2",
    "sensorId": "ce6c2312-eec1-445b-837d-43d66148ff49",
    "temperature": 500,
    "at": "2017-12-06T23:44:43.111Z"
}
```
#### **Error Responses**
* **Condition:** If sensor id or temperature values is invalid <br>
* **Code** `400 BAD REQUEST`

#### **Example**
```
curl -X POST -v http://localhost:9092/temperature-statistics/api/v1/sensors/ce6c2312-eec1-445b-837d-43d66148ff49/measurement -H 'Content-Type: application/json' -d '{"temperature": 500}'

```

### Get sensor's  measurements statistics
* **Description:** Fetch sensor statistics: average for last hour, average for last 7 days, max for last 30 days
* **URL**
`/temperature-statistics/api/v1/sensors/{uuid}/statistics`
* **Method**
`GET`

#### **Success Response**
* **Condition:** If sensor id is valid <br>
* **Code** `200 OK`
* **Response Payload**:
 ```
 {
     "sensorUuid": "ce6c2312-eec1-445b-837d-43d66148ff49",
     "averageLastHour": 101.5,
     "averageLast7Days": 101.5,
     "maxLast30Days": 200
 }
 ```
 
#### **Error Responses**
* **Condition:** If sensor id is invalid
* **Code** `400 BAD REQUEST`

#### **Example**
```
curl -v http://localhost:9092/temperature-statistics/api/v1/sensors/ce6c2312-eec1-445b-837d-43d66148ff49/statistics

```

### Get sensor's events
* **Description:** Fetch sensor statistics: average for last hour, average for last 7 days, max for last 30 days
* **URL**
`/temperature-statistics/api/v1/sensors/{uuid}/events`
* **Method**
`GET`

#### **Success Response**
* **Condition:** If sensor id is valid <br>
* **Code** `200 OK`
* **Response Payload**:
```
{
    "sensorUuid": "ce6c2312-eec1-445b-837d-43d66148ff49",
    "events": [
        {
            "type": "TEMPERATURE_EXCEEDED",
            "at": "2017-12-07T08:13:52.033Z",
            "temperature": 190.2
        }
    ]
}
```
#### **Error Responses**
* **Condition:** If sensor id is invalid
* **Code** `400 BAD REQUEST`

#### **Example**
```
curl -v http://localhost:9092/temperature-statistics/api/v1/sensors/ce6c2312-eec1-445b-837d-43d66148ff49/events

```


## Further improvements
* Demoralize sensor's statistics on separate database table. And recalculate it by schedule. 
This allows to make /statistics endpoint respond in constant time and memory.
* Add batch endpoints to:
    ** submit measurements for multiply sensors in one request
    ** get statistics for multiply sensors in one request
    ** get events for multiply sensors in one request
* Logic of checking if event should be triggered could be improved in different ways, depending on environment     

## Swagger documentation UI
Swagger UI documentation could be reachable via the [link](http://localhost:9092/swagger-ui.html)

## Environment
macOS Sierra (version 10.12.4)
Java(TM) SE Runtime Environment (build 1.8.0_66-b17)
