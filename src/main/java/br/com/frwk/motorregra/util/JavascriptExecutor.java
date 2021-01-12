package br.com.frwk.motorregra.util;

import java.io.StringWriter;
import java.util.Iterator;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavascriptExecutor {
	private ScriptEngine engine;
	ScriptContext context;
	StringWriter writer;

	public JavascriptExecutor(JsonArray parametros, JsonArray variaveis, String entrada) throws ScriptException {
		this.engine = new ScriptEngineManager().getEngineByName("javascript");
		this.context = this.engine.getContext();
		this.writer = new StringWriter();
		this.context.setWriter(this.writer);
		incluirVariaveis(parametros);
		atribuir("entrada", entrada);
		incluirVariaveis(variaveis);
	}

	public void incluirVariaveis(JsonArray variaveis) throws ScriptException {
		Iterator<JsonElement> iteretor = variaveis.iterator();
		while (iteretor.hasNext()) {
			JsonObject jsonObject = iteretor.next().getAsJsonObject();
			if (!"entrada".equals(jsonObject.get("nome").getAsString())) {
				String valor = "null";
				if (jsonObject.get("valor") != null && (!jsonObject.get("valor").isJsonNull())) {
					if ("integer".equals(jsonObject.get("type").getAsString())
							|| "boolean".equals(jsonObject.get("type").getAsString())) {
						valor = jsonObject.get("valor").getAsString();
					} else if ("saida".equals(jsonObject.get("nome").getAsString())) {
						valor = new Gson().toJson(jsonObject.get("valor"));
					} else {
						valor = executarExpressao(jsonObject.get("valor").getAsString());
					}
				}
				try {
					String codigo = "var " + (jsonObject.get("id") != null ? jsonObject.get("id").getAsString()
							: jsonObject.get("nome").getAsString()) + " = " + valor + ";";
					log.debug(codigo);
					engine.eval(codigo);
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
		}
	}

	public String executarExpressao(String expressao) throws ScriptException {
		String codigo = "JSON.stringify(" + expressao + ")";
		log.debug(("Expressao executada " + codigo));
		String result = ((String) engine.eval(codigo));
		log.debug(result);
		return result;
	}

	public String atribuir(String nomeVariavel, String expressao) throws ScriptException {
		String valorExpressao = executarExpressao(expressao);
		engine.eval(nomeVariavel + " = " + valorExpressao + ";");
		log.debug("Variavel: " + nomeVariavel + " = " + engine.eval(nomeVariavel));
		String codigo = "JSON.stringify(" + nomeVariavel + ")";
		return (String) engine.eval(codigo);
	}

}
