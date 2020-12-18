package br.com.frwk.Services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.frwk.JpaRepository.RepositoryModelos;
import br.com.frwk.entity.PastasModelos;
import br.com.frwk.entity.Regras;

@Service
public class ModeloService {
	
	@Autowired
	RepositoryModelos repository;
	
	public List<PastasModelos> getModelo() {
		return repository.findAll();
	}
	
	public Optional<PastasModelos> getModeloById(String id) {
		return repository.findById(id);
	}
	
	public PastasModelos saveModelo(PastasModelos objeto) {
		return repository.saveAndFlush(objeto);
	}
	public void deleteModelo(String id) {
		repository.deleteById(id);
	}
}
