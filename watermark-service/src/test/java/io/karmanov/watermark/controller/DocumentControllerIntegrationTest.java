package io.karmanov.watermark.controller;

import io.karmanov.watermark.domain.Document;
import io.karmanov.watermark.repository.DocumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/insert_test_data.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/delete_test_data.sql")
public class DocumentControllerIntegrationTest {

    @Value("http://localhost:${local.server.port}/watermark-service/documents")
    protected String baseUrl;

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    public void getAllDocuments() throws Exception {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(baseUrl).prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("", hasSize(3))
            .body("[0].title", is("The Dark Code"))
            .body("[0].id", is(1))
            .body("[1].title", is("How to make money"))
            .body("[1].id", is(2))
            .body("[2].title", is("Journal of human flight routes"))
            .body("[2].id", is(3))
        ;
        //@formatter:on
    }

    @Test
    public void getAllDocuments_pageable() throws Exception {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(baseUrl + "?page=0&size=1").prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("entries", hasSize(1))
            .body("entries[0].title", is("The Dark Code"))
            .body("totalElements", is(3))
            .body("totalPages", is(3))
        ;
        //@formatter:on
    }

    @Test
    public void getDocumentById_existing() throws Exception {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(baseUrl + "/1").prettyPeek()
        .then()
            .statusCode(OK.value())
            .body("id", is(1))
            .body("title", is("The Dark Code"))
        ;
        //@formatter:on
    }

    @Test
    public void getDocumentById_not_existing() throws Exception {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(baseUrl + "/100").prettyPeek()
        .then()
            .statusCode(NOT_FOUND.value())
            .body("message", is("Could not found document with id 100"))
        ;
        //@formatter:on
    }

    @Test
    public void delete_existing() throws Exception {
        Long documentId = 1L;
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .delete(baseUrl + "/" + documentId).prettyPeek()
        .then()
            .statusCode(NO_CONTENT.value())
        ;
        //@formatter:on

        Optional<Document> documentOpt = documentRepository.findById(documentId);
        assertFalse(documentOpt.isPresent());
    }

    @Test
    public void delete_not_existing() throws Exception {
        Long documentId = 100L;
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
        .when()
            .get(baseUrl + "/" + documentId).prettyPeek()
        .then()
            .statusCode(NOT_FOUND.value())
            .body("message", is("Could not found document with id " + documentId))
        ;
        //@formatter:on
    }

    @Test
    public void create_book_valid() throws Exception {
        //@formatter:off
        int createdId = given()
            .contentType(APPLICATION_JSON_VALUE)
            .body("{\n" +
"                     \"title\": \"Sample title\",\n" +
                    "  \"author\": \"Sample author\",\n" +
                    "  \"type\": 1,\n" +
                    "  \"topic\": 1\n" +
                "}")
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(CREATED.value())
            .body("id", notNullValue())
            .body("title", is("Sample title"))
            .body("type", is(1))
            .body("author", is("Sample author"))
            .body("topic", is(1))
            .extract().jsonPath().getInt("id");
        //@formatter:on

        Optional<Document> documentOpt = documentRepository.findById(Long.valueOf(createdId));
        assertTrue(documentOpt.isPresent());
    }

    @Test
    public void update_book_valid() throws Exception {
        final Long documentId = 1L;

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body("{\n" +
"                     \"title\": \"Sample title\",\n" +
                    "  \"author\": \"Sample author\",\n" +
                    "  \"type\": 1,\n" +
                    "  \"topic\": 1\n" +
                "}")
        .when()
            .put(baseUrl + "/" + documentId).prettyPeek()
        .then()
            .statusCode(ACCEPTED.value())
            .body("id", is(1))
            .body("title", is("Sample title"))
            .body("type", is(1))
            .body("author", is("Sample author"))
            .body("topic", is(1));
        //@formatter:on

        Optional<Document> documentOpt = documentRepository.findById(Long.valueOf(documentId));
        assertTrue(documentOpt.isPresent());
    }

    @Test
    public void create_book_invalid_title_is_null() throws Exception {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body("{\n" +
                    "  \"author\": \"Sample author\",\n" +
                    "  \"type\": 1,\n" +
                    "  \"topic\": 1\n" +
                "}")
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(BAD_REQUEST.value());
        //@formatter:on
    }

    @Test
    public void create_book_invalid_author_is_null() throws Exception {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body("{\n" +
                    " \"title\": \"Sample title\",\n" +
                    "  \"type\": 1,\n" +
                    "  \"topic\": 1\n" +
                "}")
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(BAD_REQUEST.value());
        //@formatter:on
    }

    @Test
    public void create_book_invalid_topic_not_valid() throws Exception {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body("{\n" +
                    "\"title\": \"Sample title\",\n" +
                    "  \"author\": \"Sample author\",\n" +
                    "  \"type\": 1,\n" +
                    "  \"topic\": 10\n" +
                "}")
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(BAD_REQUEST.value());
        //@formatter:on
    }

    @Test
    public void create_book_invalid_type_is_not_valid() throws Exception {
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body("{\n" +
                    " \"title\": \"Sample title\",\n" +
                    "  \"author\": \"Sample author\",\n" +
                    "  \"type\": 10,\n" +
                    "  \"topic\": 1\n" +
                "}")
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(BAD_REQUEST.value());
        //@formatter:on
    }

    @Test
    public void create_journal_valid() throws Exception {
        //@formatter:off
        int createdId = given()
            .contentType(APPLICATION_JSON_VALUE)
            .body("{\n" +
"                     \"title\": \"Sample title\",\n" +
                    "  \"author\": \"Sample author\",\n" +
                    "  \"type\": 2\n" +
                "}")
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(CREATED.value())
            .body("id", notNullValue())
            .body("title", is("Sample title"))
            .body("type", is(2))
            .body("author", is("Sample author"))
            .body("topic", nullValue())
            .extract().jsonPath().getInt("id");
        //@formatter:on

        Optional<Document> documentOpt = documentRepository.findById(Long.valueOf(createdId));
        assertTrue(documentOpt.isPresent());
    }
}
