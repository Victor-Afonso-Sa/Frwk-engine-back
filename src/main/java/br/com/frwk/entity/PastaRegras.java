package br.com.frwk.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "pastaregras")
public class PastaRegras {

	@Id
	@Column(name = "idpasta")
	private String idpasta;

	@Column(name = "nome")
	private String nome;
	@Column(name = "regras")
	private String regras;
	
//	
}
