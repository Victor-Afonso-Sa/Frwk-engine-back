package br.com.frwk.motorregra.acao.impl;

import javax.script.ScriptException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class ManipularArray extends Acao {

	private String expressao;
	private String array;
	private String metodo;

	public ManipularArray(JsonArray variaveis, String expressao, String array, String metodo) {
		super(null, variaveis, "ManipulacaoArray");
		this.expressao = expressao;
		this.array = array;
		this.metodo = metodo;
	}

	@Override
	public JsonObject executar(JavascriptExecutor executor) throws Exception {
		JsonObject trilha = getTrilha(executor);
		try {
			String expressaoFeita = executor.executarExpressao(expressao);
			String stm = "";
			if ("adicionar".equals(this.metodo)) {
				stm += array + ".push(" + expressaoFeita + ")";
			} else if ("remover".equals(this.metodo)) {
				stm += array + ".splice(" + expressaoFeita + ", 1)";
			}
			executor.executarExpressao(stm);
			trilha.addProperty("valor", expressaoFeita);
		} catch (Exception e) {
			return getExcecao(e, trilha, executor);
		}
		return trilha;
	}

	@Override
	public JsonObject getTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = criarTrilha(executor);
		trilha.addProperty("tipo", "manipularArray");
		trilha.addProperty("array", array);
		trilha.addProperty("metodo", metodo);
		trilha.addProperty("expressao", expressao);
		return trilha;
	}

}
