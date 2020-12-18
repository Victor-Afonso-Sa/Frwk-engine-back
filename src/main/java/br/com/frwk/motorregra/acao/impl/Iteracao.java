package br.com.frwk.motorregra.acao.impl;

import java.util.List;

import javax.script.ScriptException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Iteracao extends Acao {
	public Iteracao(List<Acao> itens, JsonArray variaveis, String nome, String array, String item) {
		super(itens, variaveis, nome);
		this.array = array;
		this.item = item;
	}

	String array;
	String item;

	@Override
	public void executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
		inicializarVariaveis(executor);
		Gson gson = new Gson();
		String json = "[{ \"nome\" :\"" + item + "\", \"valor\":null}]";
		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(json);
		JsonArray var = tradeElement.getAsJsonArray();
		executor.incluirVariaveis(var);
		String expressao = array + ".length";
		Integer tamanhoArray = Integer.valueOf(executor.executarExpressao(expressao));
		try {
			for (Integer i = 0; i < tamanhoArray; i++) {
				executor.atribuir(item, array + "[" + i + "]");
				executarItens(executor);
			}
		} catch (BreakExeption exeption) {
			
		}
	}

}
