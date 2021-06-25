package testServicos;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;

import org.junit.Test;

import io.restassured.path.json.JsonPath;
import utils.RestUtils;

public class ConsultaCEPTest {
	
	String url = "http://viacep.com.br/ws/";

	@Test
	public void validaStatusCode() {
		// http://viacep.com.br/ws/{{CEP}}/json/
		String cep = "04055041";
		String endpoint = cep.concat("/json/");

		RestUtils.setUrl(url);
		RestUtils.get(endpoint);

		assertEquals(200, RestUtils.getStatusCode());
	}
	
	@Test
	public void validaDadosCEP() {
		String cep = "04055041";
		String endpoint = cep.concat("/json/");
		LinkedHashMap<String, String> header = new LinkedHashMap<>();
		header.put("cliente-id", "curso");
		header.put("Authorization", "Basic Z3VzdGF2bzp0ZXN0ZQ==");

		RestUtils.setUrl(url);
		RestUtils.get(endpoint, header);
		
		assertEquals(200, RestUtils.getStatusCode());
		assertEquals("Rua Mauro", RestUtils.getJson("logradouro"));
		assertEquals("Saúde", RestUtils.getJson("bairro"));
		assertEquals("São Paulo", RestUtils.getJson("localidade"));
	}
	
	@Test
	public void validaDadosCEPParam() {
		String cep = "04055041";
		String endpoint = cep.concat("/json/");
		LinkedHashMap<String, String> param = new LinkedHashMap<>();
		param.put("cliente-id", "curso");
		param.put("Authorization", "Basic Z3VzdGF2bzp0ZXN0ZQ==");

		RestUtils.setUrl(url);
		RestUtils.getParams(endpoint, param);
		
		JsonPath json = RestUtils.getResponse().getBody().jsonPath();
		assertEquals(200, RestUtils.getStatusCode());
		assertEquals("Rua Mauro", RestUtils.getJson("logradouro"));
		assertEquals("Saúde", RestUtils.getJson("bairro"));
		assertEquals("São Paulo", RestUtils.getJson("localidade"));
	}

	
	
	
}
