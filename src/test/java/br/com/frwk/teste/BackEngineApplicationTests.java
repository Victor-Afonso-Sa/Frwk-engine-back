package br.com.frwk.teste;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.frwk.Services.RegraService;
import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.impl.Regra;
import br.com.frwk.motorregra.util.ConstrutorRegra;
import br.com.frwk.motorregra.util.JavascriptExecutor;

@SpringBootTest
class BackEngineApplicationTests {

//	@Test
//	void contextLoads() {
//	}
	
	@Test
	void test() throws ScriptException, RetornoExeption, BreakExeption {
		Gson gson = new Gson();
		RegraService regraService = new RegraService();
		JsonObject json = convertFileToJSON("C:\\Users\\HP\\Documents\\Projects\\Generic\\engine\\engine-front\\src\\assets\\test.json");
		ConstrutorRegra construtor = new ConstrutorRegra(regraService);
		Regra regra = construtor.criarRegra(json);
		String entrada = "[{\"tipo\" : \"debito\",\"valor\" : 150},{\"tipo\" : \"credito\",\"valor\" : 100},{\"tipo\" : \"debito\",\"valor\" : 970},{\"tipo\" : \"debito\",\"valor\" : 150},{\"tipo\" : \"credito\",\"valor\" : 1500},{\"tipo\" : \"debito\",\"valor\" : 330},]";
		JavascriptExecutor executor = new JavascriptExecutor(regra.getVariaveis(),entrada);
		regra.executar(executor); 
		withSuccess();
	}
	
	public static JsonObject convertFileToJSON (String fileName){
        JsonObject jsonObject = new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(fileName));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (FileNotFoundException e) {
           
        } catch (IOException ioe){
         
        }    
        return jsonObject;
    }
	public void jsonExecutor(Regra regra) throws ScriptException, RetornoExeption, BreakExeption {
		;
	}
}
