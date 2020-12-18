package br.com.frwk.motorregra.acao.impl;

import java.util.List;

import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.Services.RegraService;
import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.ConstrutorRegra;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class ExecutarRegra extends Acao {
	private String idRegra;
	private String entrada;
	private String retorno;
	private RegraService regraService;
	
	public void setRegraService(RegraService regraService) {
		this.regraService = regraService;
	}

	public ExecutarRegra(String idRegra, String entrada, String retorno) {
		super(null, null, "Executar regra");
		this.idRegra = idRegra;
		this.entrada = entrada;
		this.retorno = retorno;
	}

	@Override
	public void executar(JavascriptExecutor executor) throws ScriptException, RetornoExeption, BreakExeption {
		JsonObject json = regraService.getRegraSchema(this.idRegra);
		ConstrutorRegra construtor = new ConstrutorRegra(this.regraService);
		Regra regraExe = construtor.criarRegra(json);
		String expressaoEntrada = executor.executarExpressao(this.entrada);
		JavascriptExecutor ex = new JavascriptExecutor(regraExe.getVariaveis(), expressaoEntrada);
		try {
			regraExe.executar(ex);
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (RetornoExeption e) {
			String result = ex.executarExpressao("saida");
			executor.atribuir(this.retorno, result);
		} catch (BreakExeption e) {}
	}

}
