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

    @Test
    public void checkCountryInResponseBody2(){
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu doğrulayınız

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .statusCode(200)
                .body("places[0].state",equalTo("California"))
        ;
    }

    @Test
    public void checkHasItem(){
        // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
        // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
        // olduğunu doğrulayınız

        given()
                .when()
                .get("http://api.zippopotam.us/tr/01000")
                .then()
                .log().body()
                .body("places.'place name'",hasItem("Dörtağaç Köyü")) // places içindeki bütün elemanların içinde Dörtağaç köyü varmı ?
        ;
    }

    @Test
    public void Soru2(){
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
        // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places",hasSize(1)) // places in eleman uzunluğu 1 mi ?
                .body("places[0].state",equalTo("California"))
                .body("places.'place name'",hasItem("Beverly Hills"))
        ;
    }

    @Test
    public void pathParamTest(){
        given()
                .pathParams("ulke","us")
                .pathParams("postaKodu",90210)
                .log().uri() // oluşacak endpoint i yazdıralım

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKodu}") //path param

                .then()
                .log().body()
        ;
    }

    @Test
    public void queryParamTest(){
        //https://gorest.co.in/public/v1/users?page=1

        given()
                .param("page",3)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users") // url ile uri ayrıldı

                .then()
                .log().body()
        ;
    }

    @Test
    public void queryParamTest2(){
        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 1; i <= 10; i++) {

            given()
                    .param("page",i)
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users") // url ile uri ayrıldı

                    .then()
                    .log().body()
                    .body("meta.pagination.page",equalTo(i))
            ;
        }
    }
}
