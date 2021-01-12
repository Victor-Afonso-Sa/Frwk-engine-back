package br.com.frwk.motorregra.acao.impl;

import java.util.List;

import javax.script.ScriptException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Iteracao extends Acao {
	public Iteracao(List<Acao> itens, JsonArray variaveis, JsonArray variaveisLocal, String nome, String array,
			String item, String indice) {
		super(itens, variaveis, nome);
		this.array = array;
		this.item = item;
		this.indice = indice;
		this.variaveisLocal = variaveisLocal;
	}

	String array;
	String item;
	String indice;
	JsonArray variaveisLocal;

	@Override
	public JsonObject executar(JavascriptExecutor executor) throws Exception {
		executor.incluirVariaveis(variaveisLocal);
		Gson gson = new Gson();
		String json = "[{ \"nome\" :\"" + item + "\", \"valor\":null},{ \"nome\" :\"" + this.indice
				+ "\", \"valor\":null}]";
		JsonParser parser = new JsonParser();

		JsonElement tradeElement = parser.parse(json);
		JsonArray var = tradeElement.getAsJsonArray();
		executor.incluirVariaveis(var);
		String expressao = array + ".length";
		Integer tamanhoArray = Integer.valueOf(executor.executarExpressao(expressao));
		JsonObject trilha = criarTrilha(executor);
		trilha.addProperty("tipo", "iteracao");
		trilha.addProperty("array", executor.executarExpressao(array));
		trilha.add("iteracoes", new JsonArray());
		for (Integer i = 0; i < tamanhoArray; i++) {
			executor.atribuir(this.indice, i.toString());
			executor.atribuir(item, array + "[" + i + "]");
			JsonObject loop = getTrilha(executor);
			loop.add("items", new JsonArray());
			for (Acao acao : getItens()) {
				try {
					loop.get("items").getAsJsonArray().add(executarItens(executor, acao));
				} catch (RetornoExeption e) {
					trilha.get("iteracoes").getAsJsonArray().add(loop);
					e.setTrilha(trilha);
					throw e;
				} catch (Exception e) {
					trilha.get("items").getAsJsonArray().add(getExcecao(e, trilha, executor));
					break;
				}
			}
			trilha.get("iteracoes").getAsJsonArray().add(loop);
		}
		return trilha;
	}

	@Override
	public JsonObject getTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = criarTrilha(executor);
		trilha.addProperty("item", executor.executarExpressao(item));
		trilha.addProperty("indice", executor.executarExpressao(indice));
		return trilha;
	}

}
