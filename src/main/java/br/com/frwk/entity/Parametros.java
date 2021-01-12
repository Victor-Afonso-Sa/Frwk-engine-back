package br.com.frwk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "parametros")
public class Parametros {
	@Id
	@Column(name = "idpasta")
	private String idpasta;
	@Column(name = "nome")
	private String nome;
	@Column(name = "parametros")
	private String parametros;
}
