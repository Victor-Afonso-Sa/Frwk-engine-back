package br.com.frwk.motorregra.acao.impl;

import javax.script.ScriptException;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Break extends Acao{

	public Break() {
		super(null, null, "Break");
	}

	@Override
	public void executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
	throw new BreakExeption();
	}

	
}
