package br.com.frwk.motorregra.acao.impl;

import java.util.List;

import javax.script.ScriptException;

import com.google.gson.JsonArray;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Retorno extends Acao{

	public Retorno() {
		super(null, null, "Retorno");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
		throw new RetornoExeption();
	}

}
