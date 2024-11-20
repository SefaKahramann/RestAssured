package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

// GoRest Posts kaynağındaki API leri test ediniz.
// create,getId,update, delete, deleteNegative
public class _08_GoRestPostTest {
    RequestSpecification requestSpec;
    Faker faker = new Faker();
    int userIdStr;
    int PostId;

    @BeforeClass
    public void setup() {

        baseURI = "https://gorest.co.in/public/v2/posts";
        requestSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer ab4c496f93f87d4a03f9a0edcf4710e6d4c6c939c0655124ebe6de0e7c46e70a")// Token
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test()
    public void GetPostList() {
        userIdStr =
                given()
                        .spec(requestSpec)
                        .log().uri()

                        .when()
                        .get()

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("user_id[0]")
        ;
        System.out.println("userId = " + userIdStr);
    }

    @Test(dependsOnMethods = "GetPostList")
    public void createPost() {
        String userIdStr1=String.valueOf(userIdStr);
        Map<String, String> newUser = new HashMap<>();
        newUser.put("user_id",userIdStr1);
        newUser.put("title", faker.lorem().sentence());
        newUser.put("body", faker.lorem().paragraph());


        PostId =
                given()
                        .spec(requestSpec)
                        .body(newUser)

                        .when()
                        .post()

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
        System.out.println("userId = " + PostId);
    }

    @Test(dependsOnMethods = "createPost")
    public void GetPostByID() {

        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .get("/" + PostId)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(PostId))
        ;
    }

    @Test(dependsOnMethods = "GetPostByID")
    public void updatePost() {
        String userIdStr1=String.valueOf(userIdStr);
        Map<String, String> newUser = new HashMap<>();
        newUser.put("user_id",userIdStr1);
        newUser.put("title", "Sefa "+faker.lorem().sentence());
        newUser.put("body", faker.lorem().paragraph());
        given()

                .spec(requestSpec)
                .body(newUser)
                .log().uri()

                .when()
                .put("/" + PostId)

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "updatePost")
    public void deletePost() {

        given()
                .spec(requestSpec)
                .log().uri()

                .when()
                .delete("/" + PostId)

                .then()
                .log().body()
                .statusCode(204)
        ;
    }

//    @Test(dependsOnMethods = "deletePost")
//    public void deletePostNegative() {
//
//        given()
//                .spec(requestSpec)
//                .log().uri()
//
//                .when()
//                .delete("/" + PostId)
//
//                .then()
//                .log().body()
//                .statusCode(404)
//        ;
//    }
}
