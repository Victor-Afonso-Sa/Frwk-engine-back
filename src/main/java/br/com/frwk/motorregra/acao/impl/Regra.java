package br.com.frwk.motorregra.acao.impl;

import java.util.List;

import javax.script.ScriptException;

import com.google.gson.JsonArray;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Regra extends Acao{

	public Regra(List<Acao> itens, JsonArray variaveis, String nome) {
		super(itens, variaveis, nome);
	}

	@Override
	public void executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
		inicializarVariaveis(executor);
		executarItens(executor);
	}

}
