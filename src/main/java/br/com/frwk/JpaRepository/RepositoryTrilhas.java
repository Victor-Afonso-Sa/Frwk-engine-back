package br.com.frwk.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.frwk.entity.Trilha;

public interface RepositoryTrilhas extends JpaRepository<Trilha, Long> {

	List<Trilha> findByIdregra(String idregra);
	
}
