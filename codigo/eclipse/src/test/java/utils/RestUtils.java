package utils;

import java.util.LinkedHashMap;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestUtils {
	
	public static String url;
	public static Response response;
	
	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		RestUtils.url = url;
		RestAssured.baseURI = url;
	}

	public static Response getResponse() {
		return response;
	}

	public static void setResponse(Response response) {
		RestUtils.response = response;
	}

	public static RequestSpecification initRequest(ContentType contentType) {
		return (RestAssured.given()
				.relaxedHTTPSValidation()
				.contentType(contentType.JSON)
				);
	}
	
	public static void get(String endpoint) {
		Response response = initRequest(ContentType.JSON)
				.when()
				.get(endpoint)
				.then()
				.extract()
				.response();
	}
	
	public static void getParams(String endpoint, LinkedHashMap<String, String> param) {
		Response response = RestUtils.initRequest(ContentType.JSON)
				.relaxedHTTPSValidation()
				.params(param)
				.when()
				.get(endpoint)
				.then()
				.statusCode(200)
				.extract()
				.response();
		setResponse(response);
	}
	
	public static void get(String endpoint,LinkedHashMap<String,String> header) {
		Response response = RestUtils.initRequest(ContentType.JSON)
				.when()
				.get(endpoint)
				.then()
				.extract()
				.response();
		setResponse(response);
	}
	
	public static Object getJson(String key){
		JsonPath json = getResponse().getBody().jsonPath();
		return json.get(key);
	}
	
	public static int getStatusCode() {
		return getResponse().statusCode();
	}
}
