import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.AssertJUnit.assertTrue;

public class miniAssignment2 {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void setup()
    {
        requestSpecification = with().baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type","application/json");
    }

  @Test
    public void getTest()
    {
        Response response = requestSpecification.get("/posts");

        assertThat(response.statusCode(),equalTo(200));
        JSONArray arr = new JSONArray(response.asString());

          assertThat(response.path("[39].userId"),is(equalTo(4)) );

        String str = "";
        for (int i= 0; i< arr.length(); i++){
            if (arr.getJSONObject(i).get("title").getClass().getSimpleName()!= str.getClass().getSimpleName()){
                assertTrue(false);

            }

    }



}
    @Test
    public void test_put()
    {
        File jsonData = new File("src//test//java//db.json");

        given().
                baseUri("https://reqres.in/api").
                body(jsonData).
                header("Content-Type","application/json").when().
                put("/users").
                then().statusCode(200).body("name",equalTo("Arun")).body("job",equalTo("Manager"));

    }

}
