package br.com.frwk.motorregra.acao.impl;

import java.util.List;

import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.Services.RegraService;
import br.com.frwk.entity.Trilha;
import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.ConstrutorRegra;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class ExecutarRegra extends Acao {
	private String idRegra;
	private String entrada;
	private String retorno;
	private RegraService regraService;
	private String executor;

	public void setRegraService(RegraService regraService) {
		this.regraService = regraService;
	}

	public ExecutarRegra(String idRegra, String entrada, String retorno, String executor) {
		super(null, null, "Executar regra");
		this.idRegra = idRegra;
		this.entrada = entrada;
		this.retorno = retorno;
		this.executor = executor;
	}

	@Override
	public JsonObject executar(JavascriptExecutor executor) throws Exception {
		JsonObject json = regraService.getRegraSchema(this.idRegra);
		JsonArray parametros = regraService.getParametros();
		ConstrutorRegra construtor = new ConstrutorRegra(this.regraService, this.idRegra);
		Regra regraExe = construtor.criarRegra(json);
		String expressaoEntrada = executor.executarExpressao(this.entrada);
		JavascriptExecutor ex = new JavascriptExecutor(parametros, regraExe.getVariaveis(), expressaoEntrada);
		JsonObject trilha = getTrilha(executor);
		try {
			trilha = regraExe.executar(ex);
		} catch (RetornoExeption e) {
			JsonObject trilhaExe = e.getTrilha();
			trilhaExe.addProperty("executor", this.executor);
			String result = ex.executarExpressao("saida");
			executor.atribuir(this.retorno, result);
			trilhaExe.get("items").getAsJsonArray().add(getTrilhaRetorno(ex));
			trilhaExe.addProperty("saida", result);
			trilha.addProperty("saidaDoExecutado", result);
			Trilha TrilhaSalva = regraService.salvarTrilha(this.idRegra, trilhaExe);
			trilha.addProperty("TrilhaDaRegraExcutada", TrilhaSalva.getIdtrilha());
			e.setTrilha(trilha);
			return trilha;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trilha;
	}

	@Override
	public JsonObject getTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = criarTrilha(executor);
		trilha.addProperty("tipo", "executarRegra");
		trilha.addProperty("regra", idRegra);
		trilha.addProperty("entradaRegra", executor.executarExpressao(entrada));
		return trilha;
	}

}
