package br.com.frwk.teste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.frwk.RestEndpoints.Endpoints;
import br.com.frwk.Services.RegraService;
import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.impl.Regra;
import br.com.frwk.motorregra.util.ConstrutorRegra;
import br.com.frwk.motorregra.util.JavascriptExecutor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootTest
class BackEngineApplicationTests {

	@Autowired
	RegraService regraService;
//	@Test
//	void contextLoads() {
//	}


	public static JsonObject convertFileToJSON(String fileName) {
		JsonObject jsonObject = new JsonObject();
		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(fileName));
			jsonObject = jsonElement.getAsJsonObject();
		} catch (FileNotFoundException e) {

		} catch (IOException ioe) {

		}
		return jsonObject;
	}

	@Test
	void testMaiorDoArray() throws Exception {
		JsonObject trilhaExe = new JsonObject();
		JsonObject json = regraService.getRegraSchema("calculo.maior");
		JsonArray parametros = regraService.getParametros();
		ConstrutorRegra construtor = new ConstrutorRegra(regraService,"calculo.maior");
		Regra regra = construtor.criarRegra(json);
		String entrada = "[1,2,3]";
		JavascriptExecutor executor = new JavascriptExecutor(parametros, regra.getVariaveis(), entrada);
		try {
			trilhaExe = regra.executar(executor);
		} catch (RetornoExeption e) {
			String retorno = executor.executarExpressao("saida");
			assertEquals("3", retorno);
		}
		log.debug(trilhaExe.toString());
	}
	@Test
	void testExtrato() throws Exception {
		JsonObject trilhaExe = new JsonObject();
		JsonArray parametros = regraService.getParametros();
		JsonObject json = regraService.getRegraSchema("calculo.regrateste1");
		ConstrutorRegra construtor = new ConstrutorRegra(regraService, "calculo.regrateste1");
		Regra regra = construtor.criarRegra(json);
		String entrada = "[{\"tipo\" : \"debito\",\"valor\" : 150},{\"tipo\" : \"credito\",\"valor\" : 100},{\"tipo\" : \"debito\",\"valor\" : 970},{\"tipo\" : \"debito\",\"valor\" : 150},{\"tipo\" : \"credito\",\"valor\" : 1600},{\"tipo\" : \"debito\",\"valor\" : 330}]";
		JavascriptExecutor executor = new JavascriptExecutor(parametros, regra.getVariaveis(),entrada);
		try {
			trilhaExe = regra.executar(executor);
		} catch (RetornoExeption e) {
			String retorno = executor.executarExpressao("saida");
			assertEquals("{\"status\":\"positivo\",\"valor\":100}", retorno);
		}
		log.debug(trilhaExe.toString());
	}
	@Test
	void testConcatenar() throws Exception {
		JsonObject trilhaExe = new JsonObject();
		JsonObject json = regraService.getRegraSchema("calculo.concatenar");
		JsonArray parametros = regraService.getParametros();
		ConstrutorRegra construtor = new ConstrutorRegra(regraService,"calculo.concatenar");
		Regra regra = construtor.criarRegra(json);
		String entrada = "[\"hello\",\" \",\"world\"]";
		JavascriptExecutor executor = new JavascriptExecutor(parametros, regra.getVariaveis(), entrada);
		try {
			trilhaExe = regra.executar(executor);
		} catch (RetornoExeption e) {
			String retorno = executor.executarExpressao("saida");
			assertEquals("\"hello world\"", retorno);
		}
		log.debug(trilhaExe.toString());
	}
}
