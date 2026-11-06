package ch.zli.m223;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GenreResourceTest {

    @Test
    public void testCreateAndListGenre() {
        given()
          .contentType("application/json")
          .body("{\"name\":\"Strategy\"}")
          .when().post("/genres")
          .then()
             .statusCode(200)
             .body("name", is("Strategy"));

        given()
          .when().get("/genres")
          .then()
             .statusCode(200)
             .body("size()", is(1));
    }
}
