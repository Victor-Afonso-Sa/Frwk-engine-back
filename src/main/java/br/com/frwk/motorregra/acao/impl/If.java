package br.com.frwk.motorregra.acao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.util.JavascriptExecutor;

public class If extends Acao {

	private List<Condicao> condicoes;
	private JsonArray condicoesParaTrilha;

	public If(List<Condicao> condicoes, String nome, JsonArray variaveis) {
		super(null, variaveis, nome);
		this.condicoes = condicoes;
	}

	@Override
	public JsonObject executar(JavascriptExecutor executor) throws Exception {
		condicoesParaTrilha = new JsonArray();
		Condicao se = null;
		Condicao senao = null;
		List<Condicao> senaoSe = new ArrayList<Condicao>();
		for (Condicao cond : condicoes) {
			if ("se".equals(cond.getTipo())) {
				se = cond;
			} else if ("senao".equals(cond.getTipo())) {
				senao = cond;
			} else {
				senaoSe.add(cond);
			}
		}
		try {
			JsonArray condicoesTrilha = new JsonArray();
			condicoesTrilha.add("se ( " + se.getExpressao() + " ) ");
			for (Condicao item : senaoSe) {
				if (item.getExpressao() != null) {
					condicoesTrilha.add("Se não se ( " + item.getExpressao() + " ) ");
				}
			}
			this.condicoesParaTrilha = condicoesTrilha;
			if (new Boolean(executor.executarExpressao(se.getExpressao()))) {
				return getTrihaSelecionada(se.executar(executor));
			} else {
				for (Condicao condicao : senaoSe) {
					if (new Boolean(executor.executarExpressao(condicao.getExpressao()))) {
						return getTrihaSelecionada(condicao.executar(executor));
					}
				}
			}
			if (senao != null) {
				return getTrihaSelecionada(senao.executar(executor));
			}

		} catch (RetornoExeption e) {
			e.setTrilha(getTrihaSelecionada(e.getTrilha()));
			throw e;
		}
		JsonObject trilha = getTrilha(executor);
		trilha.addProperty("naoAtendeuCodicoes", true);
		return trilha;
	}

	@Override
	public JsonObject getTrilha(JavascriptExecutor executor) throws ScriptException, BreakExeption {
		JsonObject trilha = new JsonObject();
		trilha.addProperty("tipo", "IF");
		trilha.addProperty("nome", getNome());
		trilha.add("variaveis", this.getVariaveisTrilha(executor));
		trilha.add("condicoes", condicoesParaTrilha);
		return trilha;
	}

	public JsonObject getTrihaSelecionada(JsonObject trilha) throws ScriptException, BreakExeption {
		trilha.addProperty("tipo", "IF");
		trilha.addProperty("nome", getNome());
		trilha.add("condicoes", condicoesParaTrilha);
		return trilha;
	}
}
