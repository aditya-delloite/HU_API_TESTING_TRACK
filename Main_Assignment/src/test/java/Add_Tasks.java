import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Add_Tasks {


    String Path_Of_Excel_File = "C:\\Users\\adityakumar3\\Desktop\\DataBase_Api\\addingTasks_db.xlsx";
    String SHEET_NAME_INSIDE_THE_EXCEL = "Sheet1";

    boolean validating_tasks =false;

    @Test(priority = 3)
    public void add_twentyTasks() throws IOException {


        int rowCount = javaUtility.getRowCount(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL);

        System.out.println(rowCount);
        String Login_From_Token = javaUtility.STORING_TOKENS_HERE.get(0);



        for (int i = 1; i <= rowCount; i++) {
            String Task = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 0);


            Map bodyParameters = new LinkedHashMap();

            bodyParameters.put("description", Task);


            Gson gson = new Gson();
            String json = gson.toJson(bodyParameters, LinkedHashMap.class);

            System.out.println(json);

            Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                    header("Authorization", "Bearer " + Login_From_Token).header("content-type", "application/json").
                    body(json)
                    .when()
                    .post()
                    .then()
                    .extract().response();


            JSONObject arr = new JSONObject(response.asString());
            System.out.println();


            System.out.println(arr.getJSONObject("data").get("description"));
            //IF DESCRIPTION MISS MATCHES WITH OUR DATA THAT WE HAVE PROVIDE FOR THE DESCRIPTION
            //boolean variable will become false else it will remain true
            if(arr.getJSONObject("data").get("description").equals(Task))
            {
                validating_tasks =true;
            }


            System.out.println(response.asString());
        }

        System.out.println(validating_tasks);
    }

    @Test(priority = 4, dependsOnMethods = "add_twentyTasks")
    public void validating_Tasks() throws IOException {

        String Unique_id = javaUtility.STORING_Ids_Here.get(0);

        int rowCount = javaUtility.getRowCount(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL);

        for (int i = 1; i <= rowCount; i++) {
            String Task = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 0);
            {
                String Login_From_Token = javaUtility.STORING_TOKENS_HERE.get(0);
                Response response = given().baseUri("https://api-nodejs-todolist.herokuapp.com/task").
                        header("Authorization", "Bearer " + Login_From_Token).header("content-type", "application/json")
                        .when()
                        .get()
                        .then()
                        .extract().response();


                JsonPath j = new JsonPath(response.asString());



                assertThat(j.getString("data[0].owner"), equalTo(Unique_id));

               if(j.getString("data[0].owner").equals(Unique_id) && validating_tasks)
                    System.out.println("TASK SUCCESSFULLY ADDED    VALIDATION PASSED");
                else
                    System.out.println("TASK DOES NOT MATCHED    VALIDATION FAILED");

            }

        }
    }
}