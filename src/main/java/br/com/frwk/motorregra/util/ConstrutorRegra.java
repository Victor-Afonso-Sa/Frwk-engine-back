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
import br.com.frwk.motorregra.acao.impl.ManipularArray;
import br.com.frwk.motorregra.acao.impl.Regra;
import br.com.frwk.motorregra.acao.impl.Retorno;

public class ConstrutorRegra {

	private RegraService regraSevice;
	private String idRegra;

	public ConstrutorRegra(RegraService regraSevice, String idRegra) {
		super();
		this.regraSevice = regraSevice;
		this.idRegra = idRegra;
	}

	public Acao criarAcao(JsonObject acao) {
		String tipo = acao.get("tipo").getAsString();
		switch (tipo) {
		case "if":
			return criarIf(acao);
		case "iteracao":
			return criarIteracao(acao);
		case "atribuicao":
			return criarAtribuir(acao);
		case "manipularArray":
			return criarManipularArray(acao);
		case "retorno":
			return criarRetorno();
		case "break":
			return criarBreak();
		case "executarRegra":
			return criarExecutarRegra(acao);
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
		String variavel = acao.get("acao").getAsJsonObject().getAsJsonObject("variavel").get("id").getAsString();
		String expressao = acao.get("acao").getAsJsonObject().get("valor").getAsString();
		return new Atribuir(variavel, expressao);
	}

	public If criarIf(JsonObject acao) {
		JsonArray a = new JsonArray();
		JsonArray itens = acao.get("itens").getAsJsonArray();
		List<Condicao> cond = new ArrayList<Condicao>();
		for (Integer i = 0; i < itens.size(); i++) {
			cond.add(criarCondicao(itens.get(i).getAsJsonObject()));
		}
		return new If(cond, acao.get("nome").getAsString(), acao.get("variaveisescopo").getAsJsonArray());
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
		return new Condicao(acoes, acao.get("variaveis").getAsJsonArray(), acao.get("variaveisLocal").getAsJsonArray(),
				acao.get("nome").getAsString(), acao.get("tipo").getAsString(), expressao);
	}

	public Iteracao criarIteracao(JsonObject acao) {
		JsonArray itens = acao.get("itens").getAsJsonArray();
		List<Acao> acoes = new ArrayList<Acao>();
		for (Integer i = 0; i < itens.size(); i++) {
			acoes.add(criarAcao(itens.get(i).getAsJsonObject()));
		}
		return new Iteracao(acoes, acao.get("variaveis").getAsJsonArray(), acao.get("variaveisLocal").getAsJsonArray(),
				acao.get("nome").getAsString(),
				acao.get("acao").getAsJsonObject().get("array").getAsJsonObject().get("id").getAsString(),
				acao.get("acao").getAsJsonObject().get("iterado").getAsString(),
				acao.get("acao").getAsJsonObject().get("indice").getAsString());

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
		ExecutarRegra regra = new ExecutarRegra(
				acao.get("acao").getAsJsonObject().get("regra").getAsJsonObject().get("idregra").getAsString(),
				acao.get("acao").getAsJsonObject().get("entrada").getAsString(),
				acao.get("acao").getAsJsonObject().get("retorno").getAsString(), this.idRegra);
		regra.setRegraService(regraSevice);
		return regra;
	}

	public Enquanto criarEnquanto(JsonObject acao) {
		JsonArray itens = acao.get("itens").getAsJsonArray();
		List<Acao> acoes = new ArrayList<Acao>();
		for (Integer i = 0; i < itens.size(); i++) {
			acoes.add(criarAcao(itens.get(i).getAsJsonObject()));
		}
		return new Enquanto(acoes, acao.get("variaveis").getAsJsonArray(), acao.get("variaveisLocal").getAsJsonArray(),
				acao.get("nome").getAsString(), acao.get("acao").getAsJsonObject().get("expressao").getAsString());
	}

	public ManipularArray criarManipularArray(JsonObject acao) {
		return new ManipularArray(acao.get("variaveis").getAsJsonArray(),
				acao.get("acao").getAsJsonObject().get("expressao").getAsString(),
				acao.get("acao").getAsJsonObject().get("array").getAsJsonObject().get("id").getAsString(),
				acao.get("acao").getAsJsonObject().get("metodo").getAsString());
	}
}
