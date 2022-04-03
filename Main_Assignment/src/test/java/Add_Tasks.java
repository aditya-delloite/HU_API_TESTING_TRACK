import com.google.gson.Gson;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Add_Tasks {




    String Path_Of_Excel_File = "C:\\Users\\adityakumar3\\Desktop\\DataBase_Api\\addingTasks_db.xlsx";
    String SHEET_NAME_INSIDE_THE_EXCEL = "Sheet1";


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
                    header("Authorization", "Bearer " + Login_From_Token).header("content-type","application/json").
                    body(json)
                    .when()
                    .post()
                    .then()
                    .extract().response();

            System.out.println(response.asString());
        }


    }
}
