import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _01_ApiTest {

    @Test
    public void Test1(){
        //1-  URL yi çağırmadan önce hazırladıkların yapıldığı bölüm : Request,Gidecek body,token
        //2-  Endpoint in çağrıldığı bölüm : endpoint in çağrılması(METOD : GET,POST,PUT,DELETE)
        //3-  Endpoint çağrıldıktan sonra ki bölüm : Response,Test(Assert),Data

        given().
                //1. bölüm ile ilgili işler burada: giden body,token
                when().
                // 2. bölümle ilgili işler burada : metod , endpoint
                then()
                // 3. bölümle ilgili işler burada : gelen data , assert, test
        ;

    }

    @Test
    public void statusCodeTest(){
        given()

                .when()

                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // dönen data kısmı
                //.log().all()// Dönen bütün bilgiler
                .statusCode(200) // dönen değermi 200 e eşitmi, assert

        ;
    }

    @Test
    public void contentTypeTest(){
        given()

                .when()

                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // dönen data kısmı
                //.log().all()// Dönen bütün bilgiler
                .statusCode(200) // dönen değermi 200 e eşitmi, assert
                .contentType(ContentType.JSON) // dönen datanın tipi JSON mı?

        ;
    }

    @Test
    public void checkCountryInResponseBody(){
        given()

                .when()

                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // dönen data kısmı
                //.log().all()// Dönen bütün bilgiler
                .statusCode(200) // dönen değermi 200 e eşitmi, assert
                .contentType(ContentType.JSON) // dönen datanın tipi JSON mı?
                .body("country",equalTo("United States"))
                // bulunud yeri (path i )vererek içerde assertion hamcrest kütüphaneisi yapıyor
        ;
    }
}
