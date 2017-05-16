package io.karmanov.watermark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.karmanov.watermark.dto.WatermarkRequestDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/insert_test_data.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/delete_test_data.sql")
public class WatermarkControllerIntegrationTest {

    @Value("http://localhost:${local.server.port}/watermark-service/watermark")
    protected String watermarkUrl;

    @Value("http://localhost:${local.server.port}/watermark-service/tickets")
    protected String ticketUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void watermark_book_check_wait_check_success() throws Exception {
        Long documentId = 1L;
        WatermarkRequestDTO request = new WatermarkRequestDTO();
        request.setDocumentId(documentId);

        //@formatter:off
        UUID ticketId = given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(watermarkUrl).prettyPeek()
        .then()
            .statusCode(CREATED.value())
            .body("id", notNullValue())
            .body("documentId", is(documentId.intValue()))
            .body("status", is(2))
            .body("document", nullValue())
            .extract().jsonPath().getUUID("id");


        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(ticketUrl + "/" + ticketId.toString()).prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("id", is(ticketId.toString()))
            .body("status", is(2))
            .body("document", nullValue())
        ;

        Thread.sleep(11000);

        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(ticketUrl + "/" + ticketId.toString()).prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("id", is(ticketId.toString()))
            .body("status", is(1))
            .body("document", notNullValue())
            .body("document.watermark", is("{content: 'BOOK', title: 'The Dark Code', author: 'Bruce Wayne', topic: 'BUSINESS'}"))
        ;
        //@formatter:on
    }

    @Test
    public void watermark_journal_check_wait_check_success() throws Exception {
        Long documentId = 3L;
        WatermarkRequestDTO request = new WatermarkRequestDTO();
        request.setDocumentId(documentId);

        //@formatter:off
        UUID ticketId = given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(watermarkUrl).prettyPeek()
        .then()
            .statusCode(CREATED.value())
            .body("id", notNullValue())
            .body("documentId", is(documentId.intValue()))
            .body("status", is(2))
            .body("document", nullValue())
            .extract().jsonPath().getUUID("id");


        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(ticketUrl + "/" + ticketId.toString()).prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("id", is(ticketId.toString()))
            .body("status", is(2))
            .body("document", nullValue())
        ;

        Thread.sleep(11000);

        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(ticketUrl + "/" + ticketId.toString()).prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("id", is(ticketId.toString()))
            .body("status", is(1))
            .body("document", notNullValue())
            .body("document.watermark", is("{content: 'BOOK', title: 'Journal of human flight routes', author: 'Clark Kent', topic: 'BUSINESS'}"))
        ;
        //@formatter:on
    }

    @Test
    public void watermark_race_conditions() throws Exception {
        Long documentId = 1L;
        WatermarkRequestDTO request = new WatermarkRequestDTO();
        request.setDocumentId(documentId);

        //@formatter:off
        UUID ticketId = given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(watermarkUrl).prettyPeek()
        .then()
            .statusCode(CREATED.value())
            .body("id", notNullValue())
            .body("documentId", is(documentId.intValue()))
            .body("status", is(2))
            .body("document", nullValue())
            .extract().jsonPath().getUUID("id");


        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(ticketUrl + "/" + ticketId.toString()).prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("id", is(ticketId.toString()))
            .body("status", is(2))
            .body("document", nullValue());

         given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(watermarkUrl).prettyPeek()
        .then()
            .statusCode(CONFLICT.value());
        //@formatter:on
    }

    @Test
    public void watermark_not_existing_document() throws Exception {
        Long documentId = 100L;
        WatermarkRequestDTO request = new WatermarkRequestDTO();
        request.setDocumentId(documentId);

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(watermarkUrl).prettyPeek()
        .then()
            .statusCode(NOT_FOUND.value());
        //@formatter:on
    }

    @Test
    public void watermark_invalid_request() throws Exception {
        WatermarkRequestDTO request = new WatermarkRequestDTO();

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(watermarkUrl).prettyPeek()
        .then()
            .statusCode(BAD_REQUEST.value());
        //@formatter:on
    }

    @Test
    public void check_not_existing_ticket() throws Exception {
        UUID ticketId = UUID.randomUUID();

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(ticketUrl + "/" + ticketId.toString()).prettyPeek()
        .then()
            .statusCode(NOT_FOUND.value())
        ;
        //@formatter:on
    }
}
