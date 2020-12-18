package br.com.frwk.motorregra.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.Services.RegraService;
import br.com.frwk.motorregra.acao.Acao;
import br.com.frwk.motorregra.acao.impl.Atribuir;
import br.com.frwk.motorregra.acao.impl.Break;
import br.com.frwk.motorregra.acao.impl.Condicao;
import br.com.frwk.motorregra.acao.impl.Enquanto;
import br.com.frwk.motorregra.acao.impl.ExecutarRegra;
import br.com.frwk.motorregra.acao.impl.If;
import br.com.frwk.motorregra.acao.impl.Iteracao;
import br.com.frwk.motorregra.acao.impl.Regra;
import br.com.frwk.motorregra.acao.impl.Retorno;

public class ConstrutorRegra {

	private RegraService regraSevice;

	public ConstrutorRegra(RegraService regraSevice) {
		super();
		this.regraSevice = regraSevice;
	}

	public Acao criarAcao(JsonObject acao) {
		String tipo = acao.get("tipo").getAsString();
		switch (tipo) {
		case "if":
			return criarIf(acao);
		case "iteracao":
			return criarIteracao(acao);
		case "atribuicao":
			return criarAtribuir(acao.get("acao").getAsJsonObject());
		case "retorno":
			return criarRetorno();
		case "break":
			return criarBreak();
		case "executarRegra":
			return criarExecutarRegra(acao.get("acao").getAsJsonObject());
		case "regra":
			return criarRegra(acao);
		case "enquanto":
			return criarEnquanto(acao);
		}
		return null;
	}

	public Break criarBreak() {
		return new Break();
	}

	public Retorno criarRetorno() {
		return new Retorno();
	}

	public Atribuir criarAtribuir(JsonObject acao) {
		String variavel = acao.getAsJsonObject("variavel").get("id").getAsString();
		String expressao = acao.get("valor").getAsString();
		return new Atribuir(variavel, expressao);
	}

	public If criarIf(JsonObject acao) {
		JsonArray itens = acao.get("itens").getAsJsonArray();
		List<Condicao> cond = new ArrayList<Condicao>();
		for (Integer i = 0; i < itens.size(); i++) {
			cond.add(criarCondicao(itens.get(i).getAsJsonObject()));
		}
		return new If(cond, acao.get("nome").getAsString());
	}

	public Condicao criarCondicao(JsonObject acao) {
		JsonArray itens = acao.get("itens").getAsJsonArray();
		List<Acao> acoes = new ArrayList<Acao>();
		for (Integer i = 0; i < itens.size(); i++) {
			acoes.add(criarAcao(itens.get(i).getAsJsonObject()));
		}
		String expressao = acao.get("acao").getAsJsonObject().has("expressao")
				? acao.get("acao").getAsJsonObject().get("expressao").getAsString()
				: null;
		return new Condicao(acoes, acao.get("variaveisLocal").getAsJsonArray(), acao.get("nome").getAsString(),
				acao.get("tipo").getAsString(), expressao);
	}

	public Iteracao criarIteracao(JsonObject acao) {
		JsonArray itens = acao.get("itens").getAsJsonArray();
		List<Acao> acoes = new ArrayList<Acao>();
		for (Integer i = 0; i < itens.size(); i++) {
			acoes.add(criarAcao(itens.get(i).getAsJsonObject()));
		}
		return new Iteracao(acoes, acao.get("variaveisLocal").getAsJsonArray(), acao.get("nome").getAsString(),
				acao.get("acao").getAsJsonObject().get("array").getAsJsonObject().get("id").getAsString(),
				acao.get("acao").getAsJsonObject().get("iterado").getAsString());

	}

	public Regra criarRegra(JsonObject acao) {
		JsonArray itens = acao.get("itens").getAsJsonArray();
		List<Acao> acoes = new ArrayList<Acao>();
		for (Integer i = 0; i < itens.size(); i++) {
			acoes.add(criarAcao(itens.get(i).getAsJsonObject()));
		}
		return new Regra(acoes, acao.get("variaveis").getAsJsonArray(), acao.get("nome").getAsString());
	}

	public ExecutarRegra criarExecutarRegra(JsonObject acao) {
		ExecutarRegra regra = new ExecutarRegra(acao.get("regra").getAsJsonObject().get("idregra").getAsString(),
				acao.get("entrada").getAsString(), acao.get("retorno").getAsString());
		regra.setRegraService(regraSevice);
		return regra;
	}

	public Enquanto criarEnquanto(JsonObject acao) {
		JsonArray itens = acao.get("itens").getAsJsonArray();
		List<Acao> acoes = new ArrayList<Acao>();
		for (Integer i = 0; i < itens.size(); i++) {
			acoes.add(criarAcao(itens.get(i).getAsJsonObject()));
		}
		return new Enquanto(acoes, acao.get("variaveis").getAsJsonArray(), acao.get("nome").getAsString(),
				acao.get("acao").getAsJsonObject().get("expressao").getAsString());

	}
}
