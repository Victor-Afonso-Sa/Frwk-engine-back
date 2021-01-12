package br.com.frwk.motorregra.acao.impl;

import javax.script.ScriptException;

import com.google.gson.JsonObject;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Retorno extends Acao {

	public Retorno() {
		super(null, null, "Retorno");
		// TODO Auto-generated constructor stub
	}

	@Override
	public JsonObject executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
		throw new RetornoExeption();
	}

	@Override
	public JsonObject getTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = criarTrilha(executor);
		trilha.addProperty("tipo", "retorno");
		trilha.addProperty("retorno", executor.executarExpressao("saida"));
		return trilha;
	}

}
