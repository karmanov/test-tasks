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
import static com.jayway.restassured.RestAssured.when;
import static io.karmanov.tms.StubDataBuilder.getTransaction;
import static io.karmanov.tms.utils.StatisticsUtils.round;
import static org.hamcrest.Matchers.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsControllerIntegrationTest {

    @Value("http://localhost:${local.server.port}/v1/statistics")
    protected String baseUrl;

    @Value("http://localhost:${local.server.port}/v1/transactions")
    protected String transactionBaseUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void emptyStatistics() throws Exception {
        //@formatter:off
        when()
            .get(baseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.OK.value());
        //@formatter:on
    }

    @Test
    public void getStatistics() throws Exception {
        Transaction transaction = getTransaction(new BigDecimal(19.87), new DateTime());
        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(transaction))
        .when()
            .post(transactionBaseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.CREATED.value());
        //@formatter:on

        //@formatter:off
        when()
            .get(baseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("sum", comparesEqualTo(new Float(19.87)))
            .body("avg", comparesEqualTo(new Float(19.87)))
            .body("max", comparesEqualTo(new Float(19.87)))
            .body("min", comparesEqualTo(new Float(19.87)))
            .body("count", equalTo(1))
        ;
        //@formatter:on
    }

}