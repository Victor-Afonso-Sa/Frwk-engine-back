package br.com.frwk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table(name = "pastasmodelos")
public class PastasModelos {

	@Id
	@Column(name = "idpasta")
	private String idpasta;

	public String getIdpasta() {
		return idpasta;
	}

	public void setIdpasta(String idpasta) {
		this.idpasta = idpasta;
	}

	@Column(name = "schemapastas")
	private String schemapastas;

	public String getSchemapastas() {
		return schemapastas;
	}

	public void setSchemapastas(Object schemapastas) {
		Gson gson = new Gson();
		String json = gson.toJson(schemapastas);
		this.schemapastas = json;
	}

}
