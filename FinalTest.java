package testcase;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class FinalTest {

	
	//Create a new user
	
	@Test
	public void createNewUser() {
		
		String userID;
		
		baseURI = "https://gorest.co.in/public/v1";
		
		JSONObject postData = new JSONObject();
		postData.put("name", "Shanti Sethi");
		postData.put("email", "shanti_sethi@kris.co");
		
		try {
		userID = given()
					.header("Content-Type", "application/json")
					.accept(ContentType.JSON)
					.body(postData.toJSONString())
				.when()
					.post("/users")
				.then()
					.statusCode(200)
					.extract().path("id");
		} catch (AssertionError e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}
