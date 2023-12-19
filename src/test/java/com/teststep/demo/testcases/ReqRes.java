package com.teststep.demo.testcases;

import org.testng.annotations.Test;

import com.teststep.demo.pojo.User;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ReqRes {
	
	
	@Test
	// Create GET Request
	public void reqResGetRequest() 
	{
		RestAssured.baseURI = "https://reqres.in/";
		RestAssured.basePath = "/api/";
		
		given()
			.queryParam("page","2")
		.when()
			.get("/users/")
		.then()
			.statusCode(200);
	}
	
	// Create Post Request
	@Test
	public void reqResPostRequest()
	{
		RestAssured.baseURI = "https://reqres.in/";
		RestAssured.basePath = "/api/";
		
		Response res = given()	
			.header("Content-Type","application/json")
			.body("{\n"
					+ "    \"name\": \"morpheus\",\n"
					+ "    \"job\": \"leader\"\n"
					+ "}")
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.extract().response();
		
		// Print response body
		res.body().prettyPrint();
	}
	
	@Test
	public void reqResPatchRequest() 
	{
		RestAssured.baseURI = "https://reqres.in/";
		RestAssured.basePath = "/api/";
		
		Response res = 
		    given()
		    	.header("Content-type", "application/json")
		    	.pathParam("userID", "3")
		    	.body("{\n"
		    			+ "    \"name\": \"morpheus\",\n"
		    			+ "    \"job\": \"zion resident\"\n"
		    			+ "}")
		    .when()	
		    	.patch("/users/{userID}")
		    .then()
		    	.statusCode(200)
		    	.extract().response();
		
		res.body().prettyPrint();
	}
	
	// Create Post Request Serialization by JSON
	@Test
	public void reqResPostRequestSerializationByJSON()
	{
		RestAssured.baseURI = "https://reqres.in/";
		RestAssured.basePath = "/api/";
			
		User userObj = new User("Ram", "QA");
			
			Response res = given()	
				.header("Content-Type","application/json")
				.body(userObj)
			.when()
				.post("/users")
			.then()
				.statusCode(201)
				.extract().response();
			
			// Print response body
			res.body().prettyPrint();
		}
		
		// JSON Schema Validator
		@Test
		public void JSONSchemaValidator()
		{
			RestAssured.baseURI = "https://reqres.in/";
			RestAssured.basePath = "/api/";
					
			User userObj = new User("Ram", "QA");
					
			given()	
				.header("Content-Type","application/json")
					.body(userObj)
				.when()
					.post("/users")
				.then()
					.body(matchesJsonSchemaInClasspath("schema/reqres/createuser.json"));			
		}
}
