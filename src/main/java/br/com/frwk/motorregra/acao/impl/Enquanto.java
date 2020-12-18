package br.com.frwk.motorregra.acao.impl;

import java.util.List;

import javax.script.ScriptException;

import com.google.gson.JsonArray;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Enquanto extends Acao {
	
	private String expressao;

	public Enquanto(List<Acao> itens, JsonArray variaveis, String nome, String expressao) {
		super(itens, variaveis, nome);
		this.expressao = expressao ;
	}

	@Override
	public void executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
		inicializarVariaveis(executor);
		try {
			while(Boolean.valueOf(executor.executarExpressao(expressao))) {
				executarItens(executor);
			}
		} catch (BreakExeption exeption) {
			
		}
	}

}
