import com.google.gson.Gson;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Create_User {


    String Path_Of_Excel_File = "C:\\Users\\adityakumar3\\Desktop\\DataBase_Api\\DataBase.xlsx";
    String SHEET_NAME_INSIDE_THE_EXCEL = "database";

    @Test
    public void create_user() throws IOException {




        int rowCount = javaUtility.getRowCount(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL);

        for (int i = 1; i <=rowCount; i++) {
            String name = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 0);
            String email = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 1);
            String password = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 2);
            String age = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 3);

            Map bodyParameters = new LinkedHashMap();

            bodyParameters.put("name", name);
            bodyParameters.put("email", email);
            bodyParameters.put("password", password);
            bodyParameters.put("age", age);

            Gson gson = new Gson();
            String json = gson.toJson(bodyParameters, LinkedHashMap.class);


            Response response = (Response) given().
                    contentType("application/json").
                    body(json).
                    when().
                    post("https://api-nodejs-todolist.herokuapp.com/user/register").
                    then().extract();

            System.out.println(response.asString());

            //Validating Credentials
            JSONObject arr = new JSONObject(response.asString());
            System.out.println();
            assertThat(arr.getJSONObject("user").get("email"),equalTo(email));

            if(arr.getJSONObject("user").get("email").equals(email))
            {
                System.out.println("VALID CREDENTIALS EMAIL GOT MATCHED");
            }
            else
            {
                System.out.println("INVALID CREDENTIAL EMAIL DOES NOT MATCHED");
            }

            //STORING THE TOKENS OF USERS WHICH WE ARE REGISTERING
              javaUtility.STORING_TOKENS_HERE.add((String) arr.get("token"));
        }


    }





}