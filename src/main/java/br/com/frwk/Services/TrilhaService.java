package br.com.frwk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.frwk.JpaRepository.RepositoryTrilhas;
import br.com.frwk.entity.Trilha;

@Service
public class TrilhaService {

	@Autowired
	RepositoryTrilhas repositoryTrilhas;

	public List<Trilha> getAllByIdRegra(String idregra) {
		return repositoryTrilhas.findByIdregra(idregra);
	}

	public Trilha SalvarTrilha(Trilha trilha) {
		return repositoryTrilhas.save(trilha);
	}

	public List<Trilha> getAll() {
		return repositoryTrilhas.findAll();
	}

	public Optional<Trilha> getById(Long id) {
		return repositoryTrilhas.findById(id);
	}

	public void deleteById(Long id) {
		repositoryTrilhas.deleteById(id);
	}
}
