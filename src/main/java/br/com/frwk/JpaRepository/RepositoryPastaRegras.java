package br.com.frwk.JpaRepository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.frwk.entity.PastaRegras;
import br.com.frwk.entity.PastasModelos;

public interface RepositoryPastaRegras extends JpaRepository<PastaRegras, String>{

}
