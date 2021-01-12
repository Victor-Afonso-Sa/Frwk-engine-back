package br.com.frwk.motorregra.acao;

import java.util.List;

import javax.script.ScriptException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.util.JavascriptExecutor;
import lombok.Data;

@Data
public abstract class Acao {
	private List<Acao> itens;
	private JsonArray variaveis;
	private JsonArray variaveisLocal;
	private String nome;

	public Acao(List<Acao> itens, JsonArray variaveis, String nome) {
		super();
		this.itens = itens;
		this.variaveis = variaveis;
		this.nome = nome;
	}

	public void inicializarVariaveis(JavascriptExecutor executor) throws ScriptException {
		executor.incluirVariaveis(variaveis);

	}

	public void inicializarOutrasVariaveis(JavascriptExecutor executor, JsonArray parametros) throws ScriptException {
		executor.incluirVariaveis(parametros);
	}

	public JsonObject executarItens(JavascriptExecutor executor, Acao acao)
			throws RetornoExeption, BreakExeption, Exception {
		return acao.executar(executor);
	}

	public abstract JsonObject executar(JavascriptExecutor executor)
			throws ScriptException, RetornoExeption, BreakExeption, Exception;

	public JsonArray getVariaveisTrilha(JavascriptExecutor executor) throws ScriptException {
		JsonArray array = new JsonArray();
		if (variaveis != null && !variaveis.isJsonNull()) {
			for (Integer i = 0; i < variaveis.size(); i++) {
				String nomeVar = variaveis.get(i).getAsJsonObject().get("nome").getAsString();
				JsonObject var = new JsonObject();
				var.addProperty("nome", nomeVar);
				var.addProperty("valor", executor.executarExpressao(nomeVar));
				array.add(var);
			}
		}
		return array;
	}

	public abstract JsonObject getTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption;

	public JsonObject criarTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = new JsonObject();
		trilha.addProperty("nome", nome);
		trilha.add("variaveis", getVariaveisTrilha(executor));
		return trilha;
	}

	public JsonObject getExcecao(Exception e, JsonObject trilha, JavascriptExecutor executor) throws Exception {
		e.getStackTrace().getClass().getSimpleName();
		if (trilha.get("items") == null || trilha.get("items").isJsonNull()) {
			trilha.add("items", new JsonArray());
		}
		for (int i = 0; i < e.getStackTrace().length; i++) {
			String element = e.getStackTrace()[i].getFileName();
			if (element.equals("Retorno.java")) {
				return getTrilhaRetorno(executor);
			} else if (element.equals("Break.java")) {
				return getTrilhaBreak(executor);
			} else {
				return getTrilhaErrork(executor, e);
			}
		}
		throw e;
	}

	public JsonObject getTrilhaBreak(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = new JsonObject();
		trilha.addProperty("tipo", "break");
		return trilha;
	}

	public JsonObject getTrilhaRetorno(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = new JsonObject();
		trilha.addProperty("tipo", "retorno");
		trilha.addProperty("retorno", executor.executarExpressao("saida"));
		return trilha;

	}

	public JsonObject getTrilhaErrork(JavascriptExecutor executor, Exception e) {
		e.printStackTrace();
		JsonObject trilha = new JsonObject();
		trilha.addProperty("tipo", "error");
		trilha.addProperty("menssagem", e.getMessage().toString());
		return trilha;
	}
}
