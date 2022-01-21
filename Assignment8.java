package testcases;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class Assignment8 {
	
	
	// 1. GET - single user not found
	@Test
	public void validateGetSingleUserNotFound() {
		System.out.println("\n*********GET - single user not found***********\n");
		baseURI = "https://reqres.in/api";
		String userId = "23";
		
		given()
			.get("/users/"+userId)
		.then()
			.statusCode(404)
			.log().status()
			.log().body();
		
	}
	
	// 2. GET - SINGLE <RESOURCE>
	@Test
	public void validateGetSingleResource() {
		System.out.println("\n******GET - SINGLE <RESOURCE>*******\n");
		baseURI = "https://reqres.in/api";
		String userId = "2";
		
		given()
			.get("/unknown/"+userId)
		.then()
			.statusCode(200)
			.body("data.name", equalTo("fuchsia rose"))
			.log().body();
		
	}
	
	// 3. GET - SINGLE <RESOURCE> NOT FOUND
	
	@Test
	public void validateGetSingleResourceNotFound() {
		
		System.out.println("\n******GET - SINGLE <RESOURCE> NOT FOUND*******\n");
		baseURI = "https://reqres.in/api";
		String userId = "23";
		
		given()
			.get("/unknown/"+userId)
		.then()
			.statusCode(404)
			.log().body();
	}
	
	// 4. GET- List <RESOURCE>
	@Test
	public void validateGetListResources() {
		System.out.println("\n******GET- List <RESOURCE>*******\n");
		baseURI = "https://reqres.in/api";
		
		
		given()
			.get("/unknown")
		.then()
			.statusCode(200)
			.body("total", equalTo(12))
			.body("per_page", equalTo(6))
			.log().body();
		
	}
	
	// 5. POST - REGISTER - SUCCESSFUL
	@Test
	public void validatePostRegisterSuccessful() {
			System.out.println("\n******POST - REGISTER - SUCCESSFUL*******\n");
			baseURI = "https://reqres.in/api";
			
			JSONObject postData = new JSONObject();
			postData.put("email", "eve.holt@reqres.in");
			postData.put("password", "pistol");
			
			given()
				.header("Content-Type", "application/json")
				.accept(ContentType.JSON)
				.body(postData.toJSONString())
			.when()
				.post("/register")
			.then()
				.statusCode(200)
				.log().body();
			
		}
	
	// 6. POST - REGISTER - UNSUCCESSFUL
	@Test
	public void validatePostRegisterUnsuccessful() {
		System.out.println("\n******POST - REGISTER - UNSUCCESSFUL*******\n");
		baseURI = "https://reqres.in/api";
		
		JSONObject postData = new JSONObject();
		postData.put("email", "eve.holt@reqres.in");
		
		given()
			.header("Content-Type", "application/json")
			.accept(ContentType.JSON)
			.body(postData.toJSONString())
		.when()
			.post("/register")
		.then()
			.statusCode(400)
			.body("error", equalTo("Missing password"))
			.log().ifValidationFails();
		
	}
	
	// 7. POST - LOGIN - UNSUCCESSFUL
	@Test
	public void validatePostLoginUnsuccessful() {
		System.out.println("\n******POST - LOGIN - UNSUCCESSFUL*******\n");
		baseURI = "https://reqres.in/api";
		
		JSONObject postData = new JSONObject();
		postData.put("email", "eve.holt@reqres.in");
		
		given()
			.header("Content-Type", "application/json")
			.accept(ContentType.JSON)
			.body(postData.toJSONString())
		.when()
			.post("/login")
		.then()
			.statusCode(400)
			.body("error", equalTo("Missing password"))
			.log().ifValidationFails();
		
	}
	
	
		//register user>>extract id and token
		//log in with above created user >>extract token
		//run get single user to find same user id>>validate name and job details
		//single <RESOURCE> use same user id>>validate details
		//then update user details>>add validations>>search user and validate again
		//patch same user>>validate response>>search user>>validate
		//delete same user>>validate code>>search user>>validate
		
		@Test
		public void test() {
			
			Object  userId, registrationToken, loginToken;
			JSONObject credentials, updateData;
			
			baseURI = "https://reqres.in/api";
			
			credentials = new JSONObject();
			credentials.put("email", "eve.holt@reqres.in");
			credentials.put("password", "pistol");
			
			//register user>>extract id and token
			 userId = extractData(baseURI, "/register", "id", credentials);
			 System.out.println(userId);
		
			 registrationToken = extractData(baseURI, "/register", "token", credentials);	
			 System.out.println(registrationToken);
			 
			//log in with above created user >>extract token
			 loginToken = extractData(baseURI, "/login"	, "token", credentials);
			 System.out.println(loginToken);
			 
			//run get single user to find same user id>>validate name and job details
			 given()
				.get("users/"+userId)
			.then()
				.statusCode(200)
				.body("data.email", equalTo("eve.holt@reqres.in"))
				.body("data.first_name", equalTo("Eve"))
				.body("data.last_name", equalTo("Holt"))
				.log().body();
			 
			//single <RESOURCE> use same user id>>validate details
			 given()
				.get("/unknown/"+userId)
			.then()
				.statusCode(200)
				.body("data.name", equalTo("aqua sky"))
				.log().body();
			 
			// update user details>>add validations
			 	updateData = new JSONObject();
			 	updateData.put("name", "John");
			 	updateData.put("job", "QA");
			 
				 try {
					given()
						.body(updateData.toJSONString())
					.when()
						.put("/users/"+userId)
					.then()
						.statusCode(200)
						.body("name", equalTo("John"))
						.body("job", equalTo("QA"))
						.log().body();
				} catch (AssertionError e1) {
					e1.printStackTrace();
				}
				 
				 // search user and validate again
				 try {
						given()
							.get("/users/"+userId)
						.then()
							.statusCode(200)
							.body("name", equalTo("John"))
							.body("job", equalTo("QA"))
							.log().body();
					} catch (AssertionError e1) {
						e1.printStackTrace();
					}
				 
				//patch same user>>validate response>>search user>>validate
				 updateData.put("name", "Tom");
				 updateData.put("job", "DEV");
				 
				 try {
					given()
						.body(updateData.toJSONString())
					.when()
						.patch("/users/"+userId)
					.then()
						.statusCode(200)
						.body("name", equalTo("Tom"))
						.body("job", equalTo("DEV"))
						.log().body();
				} catch (AssertionError e) {
					e.printStackTrace();
				}
				 
				// search user and validate again
				 try {
						given()
							.get("/users/"+userId)
						.then()
							.statusCode(200)
							.body("name", equalTo("Tom"))
							.body("job", equalTo("DEV"))
							.log().body();
					} catch (AssertionError e1) {
						e1.printStackTrace();
					}
			 
				//delete same user>>validate code>>search user>>validate
				 
				when()
					.delete("users/"+userId)
				.then()
					.statusCode(204)
					.log().body();
				System.out.println("User deleted");
			 
				//search user>>validate
				
				try {
					given()
						.get("/users/"+userId)
					.then()
						.statusCode(404)
						.log().status();
				} catch (AssertionError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
			 
			 
		}
		
		// extract value from a key
		public Object extractData(String BaseUrl, String endPoint, String getValue, JSONObject data ) {
			baseURI = BaseUrl;
			
			Object value =given()
							.header("Content-Type", "application/json")
							.accept(ContentType.JSON)
							.body(data.toJSONString())
						.when()
							.post(endPoint)
						.then()
							.extract().path(getValue);
			return value;
			
		}


}
