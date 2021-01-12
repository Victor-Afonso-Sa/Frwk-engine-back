package br.com.frwk.exeption;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RetornoExeption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JsonObject trilha;

	public JsonObject getTrilha() {
		if (trilha != null && (trilha.get("items") == null || trilha.get("items").isJsonNull())) {
			trilha.add("items", new JsonArray());
		}
		if (trilha == null || trilha.isJsonArray()) {
			trilha = new JsonObject();
		}
		return trilha;
	}

	public void setTrilha(JsonObject trilha) {
		this.trilha = trilha;
	}
}
