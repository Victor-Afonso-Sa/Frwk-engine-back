package br.com.frwk.motorregra.acao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.script.ScriptException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Regra extends Acao {

	public Regra(List<Acao> itens, JsonArray variaveis, String nome) {
		super(itens, variaveis, nome);
	}

	@Override
	public JsonObject executar(JavascriptExecutor executor) throws Exception {
		inicializarVariaveis(executor);
		JsonObject trilha = getTrilha(executor);
		trilha.add("items", new JsonArray());
		for (Acao acao : getItens()) {
			try {
				trilha.get("items").getAsJsonArray().add(executarItens(executor, acao));
			} catch (RetornoExeption e) {
				if (e.getTrilha().keySet().size() > 0) {
					trilha.get("items").getAsJsonArray().add(e.getTrilha());
				}
				e.setTrilha(trilha);
				throw e;
			} catch (Exception e) {
				trilha.get("items").getAsJsonArray().add(getExcecao(e, trilha, executor));
				return trilha;
			}
		}
		return trilha;
	}

	@Override
	public JsonObject getTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		String tempo = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
		JsonObject trilha = criarTrilha(executor);
		trilha.addProperty("entrada", executor.executarExpressao("entrada"));
		trilha.addProperty("data", tempo);
		return trilha;
	}

}
