package br.com.frwk.motorregra.util;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JavascriptExecutor {
	private ScriptEngine engine;
	ScriptContext context;
	StringWriter writer;

	public JavascriptExecutor(JsonArray variaveis, String entrada) throws ScriptException {
		this.engine = new ScriptEngineManager().getEngineByName("javascript");
		this.context = this.engine.getContext();
		this.writer = new StringWriter();
		this.context.setWriter(this.writer);
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
					if ("array".equals(jsonObject.get("type").getAsString())) {
						valor = jsonObject.get("valor").getAsString();
					} else if ("integer".equals(jsonObject.get("type").getAsString())
							|| "boolean".equals(jsonObject.get("type").getAsString())) {
						valor = jsonObject.get("valor").getAsString();
					} else {
						valor = new Gson().toJson(jsonObject.get("valor"));
					}
				}
				try {
					String codigo = "var " + jsonObject.get("nome").getAsString() + " = " + valor + ";";
					System.out.println(codigo);
					engine.eval(codigo);
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
		}
	}

	public String executarExpressao(String expressao) throws ScriptException {
		String codigo = "JSON.stringify(" + expressao + ")";
		System.out.println("Expressao " + codigo);
		String result = ((String) engine.eval(codigo));
		System.out.println(result);
		return result;
	}

	public void atribuir(String nomeVariavel, String expressao) throws ScriptException {
		System.out.println("Atribuindo variavel " + nomeVariavel);
		String valorExpressao = executarExpressao(expressao);
		engine.eval(nomeVariavel + " = " + valorExpressao + ";");
		System.out.println("Valor " + nomeVariavel + " = " + engine.eval(nomeVariavel));
	}

}
