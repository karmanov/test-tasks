package io.temperaturestats.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.temperaturestats.domain.Event;
import io.temperaturestats.domain.EventType;
import io.temperaturestats.dto.MeasurementCreateRequestDTO;
import io.temperaturestats.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static io.temperaturestats.TestDataProvider.buildMeasurementCreateRequestDTO;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles({"test"})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/insert_test_data.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/delete_test_data.sql")
public class TemperatureControllerIntegrationTest {

    @Value("http://localhost:${local.server.port}/temperature-statistics/api/v1/sensors/{uuid}")
    protected String baseUrl;

    @Autowired
    private EventRepository eventRepository;

    private static final String SENSOR_UUID = "392e55cc-d6f7-11e7-9296-cec278b6b50a";

    @Test
    public void submitMeasurements_success() {
        String sensorId = UUID.randomUUID().toString();

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .pathParam("uuid", sensorId)
            .body("{\"temperature\": 10.0}")
        .when()
            .post(baseUrl + "/measurement").prettyPeek()
        .then()
            .statusCode(CREATED.value())
            .body("sensorId", is(sensorId))
            .body("temperature", comparesEqualTo(new Float(10.0)))
            .body("id", notNullValue())
            .body("at", notNullValue())
        ;
        //@formatter:on
    }

    @Test
    public void trigger_event_TEMPERATURE_EXCEEDED() throws Exception {
        UUID sensorId = UUID.randomUUID();


        List<Event> events = eventRepository.findBySensorId(sensorId);
        assertTrue(events.isEmpty());

        submitMeasurements(sensorId, 100.0);
        submitMeasurements(sensorId, 200.0);
        submitMeasurements(sensorId, 300.0);

        events = eventRepository.findBySensorId(sensorId);
        assertEquals(1, events.size());
        assertEquals(EventType.TEMPERATURE_EXCEEDED, events.get(0).getType());
    }

    @Test
    public void triggerEvent_after_non_consecutive_calls() throws Exception {
        UUID sensorId = UUID.randomUUID();


        List<Event> events = eventRepository.findBySensorId(sensorId);
        assertTrue(events.isEmpty());

        submitMeasurements(sensorId, 100.0);
        submitMeasurements(sensorId, 200.0);
        submitMeasurements(sensorId, 30.0);

        events = eventRepository.findBySensorId(sensorId);
        assertTrue(events.isEmpty());

        submitMeasurements(sensorId, 100.0);
        events = eventRepository.findBySensorId(sensorId);
        assertTrue(events.isEmpty());

        submitMeasurements(sensorId, 200.0);
        submitMeasurements(sensorId, 96.0);

        events = eventRepository.findBySensorId(sensorId);

        assertEquals(1, events.size());
        assertEquals(EventType.TEMPERATURE_EXCEEDED, events.get(0).getType());
    }

    @Test
    public void triggerEvent_after_case01() throws Exception {
        UUID sensorId = UUID.randomUUID();


        List<Event> events = eventRepository.findBySensorId(sensorId);
        assertTrue(events.isEmpty());

        submitMeasurements(sensorId, 92.0);
        submitMeasurements(sensorId, 95.2);
        submitMeasurements(sensorId, 96.0);
        submitMeasurements(sensorId, 98.0);

        events = eventRepository.findBySensorId(sensorId);

        assertEquals(1, events.size());
        assertEquals(EventType.TEMPERATURE_EXCEEDED, events.get(0).getType());
    }

    @Test
    public void triggerEvent_after_case02() throws Exception {
        UUID sensorId = UUID.randomUUID();


        List<Event> events = eventRepository.findBySensorId(sensorId);
        assertTrue(events.isEmpty());

        submitMeasurements(sensorId, 92.0);
        submitMeasurements(sensorId, 95.2);
        submitMeasurements(sensorId, 96.0);

        submitMeasurements(sensorId, 98.0);
        submitMeasurements(sensorId, 100.0);
        submitMeasurements(sensorId, 120.0);
        submitMeasurements(sensorId, 92.0);
        submitMeasurements(sensorId, 80.0);
        submitMeasurements(sensorId, 100.0);
        submitMeasurements(sensorId, 92.0);

        events = eventRepository.findBySensorId(sensorId);

        assertEquals(1, events.size());
        assertEquals(EventType.TEMPERATURE_EXCEEDED, events.get(0).getType());
    }

    @Test
    public void triggerEvent_after_case03() throws Exception {
        UUID sensorId = UUID.randomUUID();


        List<Event> events = eventRepository.findBySensorId(sensorId);
        assertTrue(events.isEmpty());

        submitMeasurements(sensorId, 92.0);
        submitMeasurements(sensorId, 95.2);
        submitMeasurements(sensorId, 96.0);
        submitMeasurements(sensorId, 98.0);
        submitMeasurements(sensorId, 92.0);
        submitMeasurements(sensorId, 100.0);
        submitMeasurements(sensorId, 101.0);
        submitMeasurements(sensorId, 95.6);
        submitMeasurements(sensorId, 92.0);

        events = eventRepository.findBySensorId(sensorId);

        assertEquals(2, events.size());
        assertEquals(EventType.TEMPERATURE_EXCEEDED, events.get(0).getType());
        assertEquals(EventType.TEMPERATURE_EXCEEDED, events.get(1).getType());
    }



    @Test
    public void submitMeasurementsBadRequestInvalidTemperature() {
        String sensorId = UUID.randomUUID().toString();

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .pathParam("uuid", sensorId)
            .body("{\"temperature\": asd}")
        .when()
            .post(baseUrl + "/measurement").prettyPeek()
        .then()
            .statusCode(BAD_REQUEST.value())
        ;
        //@formatter:on
    }

    @Test
    public void submitMeasurementsBadRequest_invalid_uuid() {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .pathParam("uuid", "asd")
            .body("{\"temperature\": 10.0}")
        .when()
            .post(baseUrl + "/measurement").prettyPeek()
        .then()
            .statusCode(BAD_REQUEST.value())
        ;
        //@formatter:on
    }

    @Test
    public void getEventsBySensor_events_exist() {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .pathParam("uuid", SENSOR_UUID)
        .when()
            .get(baseUrl + "/events").prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("sensorUuid", is(SENSOR_UUID))
            .body("events", hasSize(3))
        ;
        //@formatter:on
    }

    @Test
    public void getEventsBySensor_no_events_exist() {
        String sensorId = UUID.randomUUID().toString();
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .pathParam("uuid", sensorId)
        .when()
            .get(baseUrl + "/events").prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("sensorUuid", is(sensorId))
            .body("events", hasSize(0))
        ;
        //@formatter:on
    }

    @Test
    public void getEventsBySensor_invalid_uuid() {
        String sensorId = "abc";
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .pathParam("uuid", sensorId)
        .when()
            .get(baseUrl + "/events").prettyPeek()
        .then()
            .statusCode(BAD_REQUEST.value())
        ;
        //@formatter:on
    }

    @Test
    public void getStatisticsBySensor_empty_statistics() {
        String sensorId = UUID.randomUUID().toString();
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .pathParam("uuid", sensorId)
        .when()
            .get(baseUrl + "/statistics").prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("sensorUuid", is(sensorId))
            .body("averageLastHour", comparesEqualTo(new Float(0.0)))
            .body("averageLast7Days", comparesEqualTo(new Float(0.0)))
            .body("maxLast30Days", comparesEqualTo(new Float(0.0)))
        ;
        //@formatter:on
    }

    @Test
    public void getStatisticsBySensor_has_statistics() {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .pathParam("uuid", SENSOR_UUID)
        .when()
            .get(baseUrl + "/statistics").prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("sensorUuid", is(SENSOR_UUID))
            .body("averageLastHour", comparesEqualTo(new Float(115.0)))
            .body("averageLast7Days", comparesEqualTo(new Float(115.0)))
            .body("maxLast30Days", comparesEqualTo(new Float(120.0)))
        ;
        //@formatter:on
    }

    @Test
    public void getStatisticsBySensor_invalid_uuid() {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .pathParam("uuid", "asd")
        .when()
            .get(baseUrl + "/statistics").prettyPeek()
        .then()
            .statusCode(BAD_REQUEST.value())
        ;
        //@formatter:on
    }

    @Autowired
    private ObjectMapper objectMapper;

    private void submitMeasurements(UUID sensorId, Double temperature) throws Exception {
        MeasurementCreateRequestDTO request = buildMeasurementCreateRequestDTO(temperature);

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .pathParam("uuid", sensorId)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(baseUrl + "/measurement").prettyPeek()
        .then()
            .statusCode(CREATED.value())
            .body("sensorId", is(sensorId.toString()))
            .body("temperature", comparesEqualTo(new Float(temperature)))
            .body("id", notNullValue())
            .body("at", notNullValue())
        ;
        //@formatter:on
    }
}
