package com.challenge.controller;

import com.challenge.dto.PageAnalysisRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnalysisControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("http://localhost:${local.server.port}/analyze")
    protected String baseUrl;

    @Test
    public void analyze_page_without_login_form() throws Exception {
        String url = "http://www.lecloud.net/post/7295452622/scalability-for-dummies-part-1-clones";
        PageAnalysisRequestDTO request = new PageAnalysisRequestDTO(url);

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("url", is(url))
            .body("htmlVersion", is("html5"))
            .body("title", is("Scalability for Dummies - Part 1: Clones"))
            .body("containsLoginForm", is(false))
            .body("headingCount", is(5))
            .body("externalLinksCount", is(26))
            .body("internalLinksCount", is(8));
        //@formatter:on
    }

    @Test
    public void analyze_page_with_login_form() throws Exception {
        String url = "https://www.spiegel.de/meinspiegel/login.html";
        PageAnalysisRequestDTO request = new PageAnalysisRequestDTO(url);

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("url", is(url))
            .body("htmlVersion", is("html4"))
            .body("title", is("Mein SPIEGEL - SPIEGEL ONLINE"))
            .body("containsLoginForm", is(true));
        //@formatter:on
    }

    @Test
    public void analyze_page_with_login_form2() throws Exception {
        String url = "https://github.com/login";
        PageAnalysisRequestDTO request = new PageAnalysisRequestDTO(url);

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("url", is(url))
            .body("htmlVersion", is("html5"))
            .body("title", is("Sign in to GitHub · GitHub"))
            .body("containsLoginForm", is(true));
        //@formatter:on
    }

    @Test
    public void analyze_page_emptyUrl() throws Exception {
        String url = "";
        PageAnalysisRequestDTO request = new PageAnalysisRequestDTO(url);

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
        //@formatter:on
    }

    @Test
    public void analyze_page_nullUrl() throws Exception {
        PageAnalysisRequestDTO request = new PageAnalysisRequestDTO(null);

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
        //@formatter:on
    }

    @Test
    public void analyze_page_notValidUrl() throws Exception {
        String url = "asdwdqdd;;";
        PageAnalysisRequestDTO request = new PageAnalysisRequestDTO(url);

        //@formatter:off
        given()
            .contentType(APPLICATION_JSON_VALUE)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .post(baseUrl).prettyPeek()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
        //@formatter:on
    }
}
