package Model;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class _04_ApiTestPOJO {

    @Test
    public void extractingJsonAll_POJO() {
        Location lacation =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().body().as(Location.class) // tüm body al location.classa (kalıba göre) cevir
                ;
        System.out.println("lacation.getCountry() = " + lacation.getCountry());
        System.out.println("lacation = " + lacation);
    }
}
