package br.com.frwk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.frwk.JpaRepository.RepositoryPastaRegras;
import br.com.frwk.entity.PastaRegras;

@Service
public class PastaRegrasService {
	@Autowired
	RepositoryPastaRegras repository;

	public List<PastaRegras> getPastaRegras() {
		return repository.findAll();
	}

	public Optional<PastaRegras> getPastaRegrasById(String id) {
		return repository.findById(id);

	}

	public PastaRegras savePastaRegras(PastaRegras objeto) {
		return repository.saveAndFlush(objeto);
	}

	public void deletePastaRegras(String id) {
		repository.deleteById(id);
	}
}
