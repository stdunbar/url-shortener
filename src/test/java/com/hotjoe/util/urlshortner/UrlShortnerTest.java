package com.hotjoe.util.urlshortner;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class UrlShortnerTest {

    @Test
    public void testShortenEndpoint() {
        String json = "{\"long_url\": \"https://www.google.com/\"}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
          .when()
                .post("/v1/shorten")
          .then()
             .statusCode(200);
    }

    @Test
    public void testShortenEndpointWithBadUrl() {
        String json = "{\"long_url\": \"thisisabadurl\"}";

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/v1/shorten")
                .then()
                .statusCode(400);
    }

    @Test
    public void testLengthenEndpoint() {
        String json = "{\"long_url\": \"https://www.google.com/\"}";

        Response postResponse = given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/v1/shorten")
                .then()
                .statusCode(200)
                        .extract().response();

        //
        // remove protocol and hostname
        //
        String shortUrl = postResponse.jsonPath().getString("link");

        shortUrl = shortUrl.substring(shortUrl.lastIndexOf('/'));

        Response getResponse = given()
                .redirects().follow(false)
                .when()
                .get(shortUrl)
                .then()
                .statusCode(302)
                .extract().response();

        String headerLocation = getResponse.getHeader("Location");

        assert (headerLocation.equals("https://www.google.com/"));
    }

    @Test
    public void testBadLengthenEndpoint() {
        String json = "{\"long_url\": \"https://www.google.com/\"}";

        Response postResponse = given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/v1/shorten")
                .then()
                .statusCode(200)
                .extract().response();

        //
        // remove protocol and hostname
        //
        String shortUrl = postResponse.jsonPath().getString("link");

        shortUrl = shortUrl.substring(shortUrl.lastIndexOf('/'));

        given()
                .redirects().follow(false)
                .when()
                .get(shortUrl + "abcde")
                .then()
                .statusCode(400)
                .extract().response();
    }

}