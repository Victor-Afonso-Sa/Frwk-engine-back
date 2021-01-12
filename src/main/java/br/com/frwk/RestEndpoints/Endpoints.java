package br.com.frwk.RestEndpoints;

import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.Services.RegraService;
import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.impl.Regra;
import br.com.frwk.motorregra.util.ConstrutorRegra;
import br.com.frwk.motorregra.util.JavascriptExecutor;

@RestController
@RequestMapping("/engine")
public class Endpoints {

	@Autowired
	RegraService regraService;

	@PostMapping("/run/{id}")
	public String run(@PathVariable(value = "id") String id, @RequestBody String entrada) throws Exception {
		JsonObject trilha = new JsonObject();
		JsonObject json = regraService.getRegraSchema(id);
		JsonArray parametros = regraService.getParametros();
		ConstrutorRegra construtor = new ConstrutorRegra(regraService, id);
		Regra regraExe = construtor.criarRegra(json);
		JavascriptExecutor executor = new JavascriptExecutor(parametros, regraExe.getVariaveis(), entrada);
		try {
			trilha = regraExe.executar(executor);
		} catch (RetornoExeption e) {
			trilha = e.getTrilha();
			trilha.get("items").getAsJsonArray().add(getTrilhaRetorno(executor));
			String saida = executor.executarExpressao("saida");
			trilha.addProperty("saida", saida);
			regraService.salvarTrilha(id, trilha);
			return saida;
		} catch (Exception e) {

		}
		String saida = executor.executarExpressao("saida");
		trilha.addProperty("saida", saida);
		regraService.salvarTrilha(id, trilha);
		return saida;
	}

	public JsonObject getTrilhaRetorno(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = new JsonObject();
		trilha.addProperty("tipo", "retorno");
		trilha.addProperty("retorno", executor.executarExpressao("saida"));
		return trilha;
	}
}
