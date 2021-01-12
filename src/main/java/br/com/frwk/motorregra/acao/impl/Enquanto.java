package br.com.frwk.motorregra.acao.impl;

import java.util.List;

import javax.script.ScriptException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Enquanto extends Acao {

	private String expressao;
	private JsonArray variaveisLocal;

	public Enquanto(List<Acao> itens, JsonArray variaveis, JsonArray variaveisLocal, String nome, String expressao) {
		super(itens, variaveis, nome);
		this.expressao = expressao;
		this.variaveisLocal = variaveisLocal;
	}

	@Override
	public JsonObject executar(JavascriptExecutor executor) throws Exception {
		inicializarOutrasVariaveis(executor, variaveisLocal);
		JsonObject trilha = getTrilha(executor);
		trilha.add("items", new JsonArray());
		while (Boolean.valueOf(executor.executarExpressao(expressao))) {
			JsonArray itemsExe = new JsonArray();
			for (Acao acao : getItens()) {
				try {
					itemsExe.add(executarItens(executor, acao));
				} catch (RetornoExeption e) {
					trilha.get("items").getAsJsonArray().add(itemsExe);
					e.setTrilha(trilha);
					throw e;
				} catch (Exception e) {
					itemsExe.add(getExcecao(e, trilha, executor));
					trilha.get("items").getAsJsonArray().add(itemsExe);
					return trilha;
				}
			}
			trilha.get("items").getAsJsonArray().add(itemsExe);
		}
		return trilha;
	}

	@Override
	public JsonObject getTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = criarTrilha(executor);
		trilha.addProperty("tipo", "enquanto");
		trilha.addProperty("expressao", expressao);
		return trilha;
	}

}
