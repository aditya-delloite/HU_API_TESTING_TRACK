package Mini_Assignment1_RestAssured;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class putRequest {
    @Test
    public void test_put()
    {
        File jsonData = new File("src//test//java/Mini_Assignment1_RestAssured//putdata.json");

        given().
                baseUri("https://reqres.in/api").
                body(jsonData).
                header("Content-Type","application/json").when().
                put("/users").
                then().statusCode(200).body("name",equalTo("Arun")).body("job",equalTo("Manager"));

    }

}
