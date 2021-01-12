package br.com.frwk.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.frwk.entity.Trilha;

public interface RepositoryTrilhas extends JpaRepository<Trilha, Long> {

	List<Trilha> findByIdregra(String idregra);
}
