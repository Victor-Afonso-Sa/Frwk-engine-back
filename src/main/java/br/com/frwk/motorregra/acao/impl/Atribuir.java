package br.com.frwk.motorregra.acao.impl;

import javax.script.ScriptException;

import com.google.gson.JsonObject;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Atribuir extends Acao {

	String variavel;
	String expressao;

	public Atribuir(String variavel, String expressao) {
		super(null, null, "Atribuição");
		this.variavel = variavel;
		this.expressao = expressao;
	}

	@Override
	public JsonObject executar(JavascriptExecutor executor) throws Exception {
		JsonObject trilha = getTrilha(executor);
		trilha.addProperty("valor", executor.atribuir(variavel, expressao));
		return trilha;
	}

	@Override
	public JsonObject getTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = criarTrilha(executor);
		trilha.addProperty("tipo", "atribuicao");
		trilha.addProperty("variavel", variavel);
		trilha.addProperty("expressao", expressao);
		return trilha;
	}

}
