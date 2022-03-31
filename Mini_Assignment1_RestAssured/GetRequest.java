package Mini_Assignment1_RestAssured;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetRequest {




    @Test
    public void test_GetRequest()
    {

            given().
                    baseUri("https://jsonplaceholder.typicode.com/posts").
                    header("Content-Type", "application/json").

                    when().
                    get("https://jsonplaceholder.typicode.com/posts").
                    then().
                    body("title[39]",equalTo("enim quo cumque")).
                    body("userId[39]",equalTo(4)).
                    statusCode(200);

        }




}
