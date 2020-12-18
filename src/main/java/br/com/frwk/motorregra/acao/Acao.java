package br.com.frwk.motorregra.acao;

import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.util.JavascriptExecutor;
import lombok.Data;

@Data
public abstract class Acao {
	private List<Acao> itens;
	private JsonArray variaveis;
	private String nome;

	public Acao(List<Acao> itens, JsonArray variaveis, String nome) {
		super();
		this.itens = itens;
		this.variaveis = variaveis;
		this.nome = nome;
	}

	public void inicializarVariaveis(JavascriptExecutor executor) throws ScriptException {
		executor.incluirVariaveis(variaveis);
	}

	public void executarItens(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
		for (Acao acao : itens) {
			acao.executar(executor);
		}
	}

	public abstract void executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption;
}
