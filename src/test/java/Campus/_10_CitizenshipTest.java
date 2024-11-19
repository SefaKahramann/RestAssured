package Campus;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class _10_CitizenshipTest extends _CampusParent{

    String citizenshipName = faker.name().fullName() + faker.number().digits(2);
    String citizenshipsShortName = faker.name().name();
    String idStr = "";


    @Test
    public void createCitizenships() {
        Map<String, String> citizenships = new HashMap<>();
        citizenships.put("id", null);
        citizenships.put("name", citizenshipName);
        citizenships.put("shortName", citizenshipsShortName);
        idStr =
                given()
                        .spec(requestSpec)
                        .log().uri()
                        .body(citizenships)

                        .when()
                        .post("/school-service/api/citizenships")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "createCitizenships")
    public void createCitizenshipsNegative() {

        Map<String, String> citizenships = new HashMap<>();

        citizenships.put("name", citizenshipName);
        citizenships.put("shortName", citizenshipsShortName);


        given()
                .spec(requestSpec)
                .log().uri()
                .body(citizenships)

                .when()
                .post("/school-service/api/citizenships")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already exists."))
        ;
    }

    @Test(dependsOnMethods = "createCitizenshipsNegative")
    public void updateCitizenships() {
        String citizenshipsname="SefalarÜlkesi"+faker.number().digits(5);
        Map<String, String> citizenships = new HashMap<>();

        citizenships.put("id", idStr);
        citizenships.put("name", citizenshipsname);
        citizenships.put("shortname", "12asd");


        given()
                .spec(requestSpec)
                .log().uri()
                .body(citizenships)

                .when()
                .put("/school-service/api/citizenships")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(citizenshipsname))
        ;
    }

    @Test(dependsOnMethods = "updateCitizenships")
    public void deleteCitizenships() {
        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .delete("/school-service/api/citizenships/"+ idStr)

                .then()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteCitizenships")
    public void deleteCitizenshipsNegative() {
        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .delete("/school-service/api/citizenships/"+ idStr)

                .then()
                .statusCode(400)
                .body("message", equalTo("Citizenships not found"))
        ;
    }

}
