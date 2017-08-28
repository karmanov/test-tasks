package io.karmanov.tms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.karmanov.tms.domain.Transaction;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static com.jayway.restassured.RestAssured.given;
import static io.karmanov.tms.StubDataBuilder.getTransaction;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {

    @Value("http://localhost:${local.server.port}/v1/transactions")
    protected String baseUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createTransaction_success() throws Exception {
        Transaction transaction = getTransaction(new BigDecimal(19.87), new DateTime());
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(transaction))
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.CREATED.value());
        //@formatter:on
    }

    @Test
    public void createTransaction_old_transaction_submitted() throws Exception {
        Transaction transaction = getTransaction(new BigDecimal(19.87), new DateTime().minusMinutes(2));
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(transaction))
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
        //@formatter:on
    }
}