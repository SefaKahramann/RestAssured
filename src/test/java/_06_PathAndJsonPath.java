import Model.Location;
import Model.Place;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class _06_PathAndJsonPath {

    @Test
    public void extractingPath() {
        // gelen body de bilgiyi dışarı almanın 2 yöntemini gördük
        // extract.path(""),as(ToDo.Class)
        String postCode =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().path("'post code'");

        System.out.println("postCode = " + postCode);
        int postCodeInt = Integer.parseInt(postCode);
        System.out.println("postCodeInt = " + postCodeInt);
    }

    @Test
    public void extractingJsonPath() {
        int postCode =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().jsonPath().getInt("'post code'")
                // 1. Avantaj : tip dönüşümü otomatik, uygun tip verilmeli
                ;

        System.out.println("postCode = " + postCode);
    }

    @Test
    public void extractingJsonPathIcNesne() {
        Response getData =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().response()
                ;
            //Location locationAsPathNesnesi=getData.as(Location.class);
            //System.out.println("locationAsPathNesnesi = " + locationAsPathNesnesi);
            // Bana sadece place dizisi lazım olsa bile , bütün diğer classları yazmak zorundayım

        // eğer içerdeki nesen tipli bir parçayı (Burada Places) almak isteseydim
        List<Place> places= getData.jsonPath().getList("places", Place.class);
        System.out.println("places = " + places);

        // Sadece Place dizisi lazım ise diğerlerini yazmak zorunda değilsin.

        // Daha önceki örneklerde (as) Class dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.

        // Burada ise(JsonPath) aradaki bir veriyi classa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ile veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
    }
}
