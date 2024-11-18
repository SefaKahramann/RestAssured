package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _07_GoRestUsersTest {
    RequestSpecification requestSpec;
    Faker faker = new Faker();
    int userId;

    @BeforeClass
    public void setup() {

        baseURI = "https://gorest.co.in/public/v2/users";
        requestSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer ab4c496f93f87d4a03f9a0edcf4710e6d4c6c939c0655124ebe6de0e7c46e70a")// Token
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void createUser() {
        Map<String, String> newUser = new HashMap<>(); // postmandaki body kısmı map olarak düzenlendi
        newUser.put("name", faker.name().fullName());
        newUser.put("gender", "male");
        newUser.put("email", faker.internet().emailAddress());
        newUser.put("status", "active");

        userId =
                given()
                        .spec(requestSpec)
                        .body(newUser)

                        .when()
                        .post() // http ile başlamıyorsa baseURI geliyor

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
        System.out.println("userId = " + userId);
    }

    @Test(dependsOnMethods = "createUser")
    public void GetUserByID() {

        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .get("/" + userId)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userId))
        ;
    }

    @Test(dependsOnMethods = "GetUserByID")
    public void updateUser() {
        Map<String, String> newUser = new HashMap<>(); // postmandaki body kısmı map olarak düzenlendi
        newUser.put("name", "Sefa " + faker.name().lastName());
        newUser.put("gender", "male");
        newUser.put("email", faker.internet().emailAddress());
        newUser.put("status", "active");
        given()

                .spec(requestSpec)
                .body(newUser)
                .log().uri()

                .when()
                .put("/" + userId)

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "updateUser") // bu aşamadan sonra hepsi çalıştırılmalı yani class çalıştırılmalı
    public void deleteUser() {

        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .delete("/" + userId)

                .then()
                .log().body()
                .statusCode(204)
        ;
    }

    // delete negative
    @Test(dependsOnMethods = "deleteUser")
    public void deleteUserNegative() {

        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .delete("/" + userId)

                .then()
                .log().body()
                .statusCode(404)
        ;
    }
}
