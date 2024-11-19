package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class _09_CountryTest {
    RequestSpecification requestSpec;
    Faker faker = new Faker();
    String countryName = faker.address().country() + faker.number().digits(2);
    String countryCode = faker.address().countryCode();
    String idStr = "";

    @BeforeClass
    public void setup() {
        baseURI = "https://test.mersys.io";

        Map<String, String> userCredential = new HashMap<>();
        userCredential.put("username", "turkeyts");
        userCredential.put("password", "TechnoStudy123");
        userCredential.put("rememberMe", "true");

        Cookies cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(userCredential)

                        .when()
                        .post("/auth/login")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response().detailedCookies();
        ;
        System.out.println("cookies = " + cookies);
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build();
    }

    @Test
    public void createCountry() {
        Map<String, String> country = new HashMap<>();
        country.put("id", null);
        country.put("name", countryName);
        country.put("code", countryCode);
        country.put("translateName", null);
        country.put("hasState", null);
        idStr =
                given()
                        .spec(requestSpec)
                        .log().uri()
                        .body(country)

                        .when()
                        .post("/school-service/api/countries")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative() {

        Map<String, String> country = new HashMap<>();

        country.put("name", countryName);
        country.put("code", countryCode);


        given()
                .spec(requestSpec)
                .log().uri()
                .body(country)

                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already exists."))
        ;
    }

    @Test(dependsOnMethods = "createCountry")
    public void updateCountry() {
        String countryName="SefalarÜlkesi"+faker.number().digits(5);
        Map<String, String> country = new HashMap<>();

        country.put("id", idStr);
        country.put("name", countryName);
        country.put("code", "SefalarÜlkesi123");


        given()
                .spec(requestSpec)
                .log().uri()
                .body(country)

                .when()
                .put("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(countryName))
        ;
    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountry() {
        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .delete("/school-service/api/countries/"+ idStr)

                .then()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteCountry")
    public void deleteCountryNegative() {
        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .delete("/school-service/api/countries/"+ idStr)

                .then()
                .statusCode(400)
                .body("message", equalTo("Country not found"))
        ;
    }
}
