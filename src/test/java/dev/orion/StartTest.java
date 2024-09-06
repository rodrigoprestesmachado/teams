package dev.orion;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
class StartTest {

    @Test
    @Order(1)
    void testCreateEndpoint() {
        given()
                .when().post("/teams/create")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("hash", notNullValue())
                .extract().response();

    }

    @Test
    void testFindTeamEndpoint() {
        String hashTeam = getHashTeam();
        given()
                .pathParam("hashTeam", hashTeam)
                .when().get("/teams/find/{hashTeam}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("hash", equalTo(hashTeam));
    }

    @Test
    void testJoinEndpoint() {
        String hashTeam = getHashTeam();
        String hashUser = "user123";

        given()
                .formParam("hashTeam", hashTeam)
                .formParam("hashUser", hashUser)
                .when().post("/teams/join")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("hash", equalTo(hashTeam));
    }

    private String getHashTeam() {
        return given()
                .when().post("/teams/create")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("hash", notNullValue())
                .extract().path("hash");
    }

}
