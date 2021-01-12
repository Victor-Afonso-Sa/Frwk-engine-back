package br.com.frwk.motorregra.acao.impl;

import java.util.List;

import javax.script.ScriptException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Condicao extends Acao {

	public Condicao(List<Acao> itens, JsonArray variaveis, JsonArray variaveisLocal, String nome, String tipo,
			String expressao) {
		super(itens, variaveis, nome);
		this.tipo = tipo;
		this.expressao = expressao;
		this.variaveisLocal = variaveisLocal;
	}

	private String tipo;
	private String expressao;
	private JsonArray variaveisLocal;

	@Override
	public JsonObject executar(JavascriptExecutor executor) throws Exception {
		inicializarOutrasVariaveis(executor, variaveisLocal);
		JsonObject trilha = getTrilha(executor);
		trilha.add("items", new JsonArray());
		for (Acao acao : getItens()) {
			try {
				trilha.get("items").getAsJsonArray().add(executarItens(executor, acao));
			} catch (RetornoExeption e) {
				e.setTrilha(trilha);
				throw e;
			} catch (Exception e) {
				trilha.get("items").getAsJsonArray().add(getExcecao(e, trilha, executor));
				return trilha;
			}
		}
		return trilha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getExpressao() {
		return expressao;
	}

	public void setExpressao(String expressao) {
		this.expressao = expressao;
	}

	@Override
	public JsonObject getTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = criarTrilha(executor);
		trilha.addProperty("condicao", tipo);
		trilha.addProperty("expressao", expressao);
		return trilha;
	}

}
