package br.com.frwk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table(name = "regras")
public class Regras {

	@Id
	@Column(name = "idregra")
	private String idRegra;

	public String getIdregra() {
		return idRegra;
	}

	public void setIdregra(String idRegra) {
		this.idRegra = idRegra;
	}

	@Column(name = "schemaregras")
	private String schemaregras;

	public String getSchemaregras() {
		return schemaregras;
	}

	public void setSchemaregras(Object schemaregras) {
		Gson gson = new Gson();
		String json = gson.toJson(schemaregras);
		this.schemaregras = json;
	}

}
