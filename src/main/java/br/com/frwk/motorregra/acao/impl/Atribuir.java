package br.com.frwk.motorregra.acao.impl;

import javax.script.ScriptException;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
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
	public void executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
		executor.atribuir(variavel, expressao);
	}

}
