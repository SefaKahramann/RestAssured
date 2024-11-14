import TaskPojo.Pojo;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _05_Task {

    @Test
    public void task1() {
        //Task 1
        //create a request to https://jsonplaceholder.typicode.com/todos/2
        //expect status 200
        //expect content type JSON
        //expect title in response body to be "quis ut nam facilis et officia qui"
        given()

                .when()

                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
        ;
    }

    @Test
    public void task2A() {
        /**
         * Task 2
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * expect status 200
         * expect content type JSON
         *a) expect response completed status to be false(hamcrest)
         *b) extract completed field and testNG assertion(testNG)
         */

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false)) //hemcrest ile assertion
        ;
    }

    @Test
    public void task2B() {
        boolean completedData =
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")
                        .then()
                        .extract().path("completed")
                ;
        Assert.assertTrue(!completedData);
    }

    @Test
    public void task3(){
        /* Task 3
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * Converting Into POJO body data and write
         */

        Pojo pojo=
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")
                        .then()
                        .extract().body().as(Pojo.class)
                ;
        System.out.println("pojo.getId() = " + pojo.getId());
        System.out.println("pojo.getTitle() = " + pojo.getTitle());
        System.out.println("pojo = " + pojo);
    }
}
