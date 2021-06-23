package testServicos;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ConsultaCEPTest {
	
	String url = "http://viacep.com.br/ws/";

	@Test
	public void validaStatusCode() {
		// http://viacep.com.br/ws/{{CEP}}/json/
		String cep = "04055041";
		String endpoint = cep.concat("/json/");

		RestAssured.baseURI = url;
		Response response = get(endpoint);

		assertEquals(200, response.getStatusCode());
	}
	
	@Test
	public void validaDadosCEP() {
		String cep = "04055041";
		String endpoint = cep.concat("/json/");
		LinkedHashMap<String, String> header = new LinkedHashMap<>();
		header.put("cliente-id", "curso");
		header.put("Authorization", "Basic Z3VzdGF2bzp0ZXN0ZQ==");

		RestAssured.baseURI = url;
		RequestSpecification request = iniRequest(ContentType.JSON, header);
		Response response = request
				.when()
				.get(endpoint)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		JsonPath json = response.getBody().jsonPath();
		
		assertEquals("Rua Mauro", json.get("logradouro"));
		assertEquals("Saúde", json.get("bairro"));
		assertEquals("São Paulo", json.get("localidade"));
	}
	
	@Test
	public void validaDadosCEPParam() {
		String cep = "04055041";
		String endpoint = cep.concat("/json/");
		LinkedHashMap<String, String> param = new LinkedHashMap<>();
		param.put("cliente-id", "curso");
		param.put("Authorization", "Basic Z3VzdGF2bzp0ZXN0ZQ==");

		RestAssured.baseURI = url;
		Response response = RestAssured.given()
			.relaxedHTTPSValidation()
			.contentType(ContentType.JSON)
			.params(param)
			.when()
			.get(endpoint)
			.then()
			.statusCode(200)
			.extract()
			.response();
		
		JsonPath json = response.getBody().jsonPath();
		
		assertEquals("Rua Mauro", json.get("logradouro"));
		assertEquals("Saúde", json.get("bairro"));
		assertEquals("São Paulo", json.get("localidade"));
	}

	private RequestSpecification iniRequest(ContentType contentType) {
		return (RestAssured.given()
				.relaxedHTTPSValidation()
				.contentType(contentType.JSON)
				);
	}
	
	private RequestSpecification iniRequest(ContentType contentType, LinkedHashMap<String, String> header) {
		return (RestAssured.given()
				.relaxedHTTPSValidation()
				.contentType(contentType.JSON)
				.headers(header)
				);
	}
	
	private Response get(String endpoint) {
		return (iniRequest(ContentType.JSON)
				.when()
				.get(endpoint)
				.then()
				.extract()
				.response()
				);
	}
}
