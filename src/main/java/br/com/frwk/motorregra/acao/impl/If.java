package br.com.frwk.motorregra.acao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class If extends Acao {
    
    private List<Condicao> condicoes;

    public If(List<Condicao> condicoes, String nome) {
		super(null, null, nome);
		this.condicoes = condicoes;
	}


	@Override
    public void executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
        Condicao se = null;
        Condicao senao = null;
        List<Condicao> senaoSe = new ArrayList<Condicao>();
        for(Condicao cond : condicoes) {
            if("se".equals(cond.getTipo())) {
                se = cond;
            } else if("senao".equals(cond.getTipo())) {
                senao = cond;
            } else {
                senaoSe.add(cond);
            }
        }
        if(new Boolean(executor.executarExpressao(se.getExpressao()))) { 
            se.executarItens(executor);
            return;
        } else {
            for(Condicao condicao : senaoSe) {
                if(new Boolean(executor.executarExpressao(condicao.getExpressao()))) {
                    condicao.executarItens(executor);
                    return;
                }
            }
        }
        if(senao != null) {
            senao.executar(executor);
        }
    }

}
