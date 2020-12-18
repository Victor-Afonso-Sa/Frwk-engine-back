package br.com.frwk.motorregra.acao.impl;

import java.util.List;

import javax.script.ScriptException;

import com.google.gson.JsonArray;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class Condicao extends Acao {
    
    public Condicao(List<Acao> itens, JsonArray variaveis, String nome, String tipo, String expressao) {
		super(itens, variaveis, nome);
		this.tipo = tipo;
		this.expressao = expressao;
	}

	private String tipo;
    private String expressao;


    @Override
    public void executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
    	inicializarVariaveis(executor);
        executarItens(executor);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getExpressao() {
        return expressao;
    }

    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }
    
    
}
