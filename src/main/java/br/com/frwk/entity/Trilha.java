package br.com.frwk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "trilha", indexes = @Index(name = "idregra", columnList = "idregra", unique = false))
public class Trilha {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idtrilha")
	private Long idtrilha;

	@Column(name = "idregra")
	private String idregra;

	@Column(name = "jsonexecucao")
	private String jsonexecucao;

	public Trilha(String idRegra, String jsonExecucao) {

		this.idregra = idRegra;
		this.jsonexecucao = jsonExecucao;
	}

	public Trilha() {

	}
}
