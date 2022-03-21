package petstore_rest_project;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class petstore_assignment {
	
	@Test()
	public void create_user()
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		JSONObject obj = new JSONObject();
		obj.put("username", "RandomUserName");
		obj.put("firstname", "RandomFirstName");
		obj.put("lastname","RandomLastName");
		obj.put("email", "random@random.com");
		obj.put("password", "randompassword");
		obj.put("phone","9123456780");
		obj.put("userStatus",0);
	     Response resp = 	given()
		.contentType(ContentType.JSON)
		.body(obj.toJSONString()).when()
		.post("/user").then().statusCode(200).log().all().extract().response();
	     
	     String message = resp.jsonPath().getString("message");
	     
	     Assert.assertEquals(message,"9223372036854775807");
		
		
		
	}
	
	
	
	
	
	@Test()
	public void user_login()
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		Response resp = given().queryParam("username", "random").queryParam("password","randompass")
				.get("/user/login").then().statusCode(200).log().all().extract().response();
		String message = resp.jsonPath().getString("message");
		Assert.assertTrue(message.contains("logged in user session"));
	}
	
	
	
	
	
	@Test()
	public void user_logout()
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		Response resp = given().get("/user/logout").then().statusCode(200).log().all().extract().response();
		String message = resp.jsonPath().getString("message");
		Assert.assertEquals(message, "ok");
	}

	
	
	
	
	
	@Test(dependsOnMethods="create_user")
	public void get_user()
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		Response resp = RestAssured.get("/user/RandomUserName");
		if(resp.getStatusCode()==200)
		{
			System.out.println(resp.asString());
		}
		if(resp.getStatusCode()==400)
		{
			System.out.println("Invalid username supplied");
			Assert.fail();
		}
		if(resp.getStatusCode()==404)
		{
			System.out.println("User not found");
			Assert.fail();
		}
	}
	
	
	
	
	
	
	@Test()
	public void put_user()
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		JSONObject obj = new JSONObject();
		obj.put("id", 1234);
		obj.put("username", "RandomUserName");
		obj.put("firstname", "RandomFirstName");
		obj.put("lastname","RandomLastName");
		obj.put("email", "random@random.com");
		obj.put("password", "randompassword");
		obj.put("phone","9123456780");
		obj.put("userStatus",0);
	     Response resp = 	given()
		.contentType(ContentType.JSON)
		.body(obj.toJSONString()).when()
		.put("/user/random_name").then().statusCode(200).log().all().extract().response();
	     
	     String message = resp.jsonPath().getString("code");
	     if(message=="400")
	     {
	    	 System.out.println("Invalid User Supplied");
	    	 Assert.fail();
	     }
	     if(message=="404")
	     {
	    	 System.out.println("User not found");
	    	 Assert.fail();
	     }
	     
	     Assert.assertEquals(message,"200");
	     
	     System.out.println("Changes made successfully");
	}
	
	
	
	
	
	
	
	@Test(dependsOnMethods="create_user")
	public void delete_user()
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		Response resp = RestAssured.delete("user/RandomUserName");
		if(resp.getStatusCode()==200)
		{
			System.out.println("User deleted");
		}
		if(resp.getStatusCode()==400)
		{
			System.out.println("Invalid user supplied");
		}
		if(resp.getStatusCode()==404)
		{
			System.out.println("user not found");
		}
	}
	
	
	
	@Test()
	public void create_with_list_object()
	{
		
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		List<JSONObject> listobj = new ArrayList<JSONObject>();
		
		JSONObject obj1 = new JSONObject();
		obj1.put("id", 1234345);
		obj1.put("username", "RandomUserName1234");
		obj1.put("firstname", "RandomFirstName1234");
		obj1.put("lastname","RandomLastName1234");
		obj1.put("email", "random@random.com");
		obj1.put("password", "randompassword");
		obj1.put("phone","9123456780");
		obj1.put("userStatus",0);
		
		
		
		JSONObject obj2 = new JSONObject();
		obj2.put("id", 1234678);
		obj2.put("username", "RandomUserName12");
		obj2.put("firstname", "RandomFirstName12");
		obj2.put("lastname","RandomLastName12");
		obj2.put("email", "random@random.com");
		obj2.put("password", "randompassword12");
		obj2.put("phone","9123456780");
		obj2.put("userStatus",0);
		
		
		listobj.add(obj1);
		listobj.add(obj2);
		
		
		
		Response resp = 	given()
				.contentType(ContentType.JSON)
				.body(listobj).when()
				.post("/user/createWithList").then().statusCode(200).log().all().extract().response();
		
		
		
		String code = resp.jsonPath().getString("code");
		
		String message = resp.jsonPath().getString("message");
		System.out.println(message);
		
		Assert.assertEquals(code, "200");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
