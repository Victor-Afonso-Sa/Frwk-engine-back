package br.com.frwk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.frwk.JpaRepository.RepositoryParametros;
import br.com.frwk.entity.Parametros;

@Service
public class ParametrosService {

	@Autowired
	RepositoryParametros repository;

	public List<Parametros> getParams() {
		return repository.findAll();
	}

	public Optional<Parametros> getParamsById(String id) {
		return repository.findById(id);
	}

	public Parametros saveParams(Parametros objeto) {
		return repository.saveAndFlush(objeto);
	}

	public void deleteParams(String id) {
		repository.deleteById(id);
	}
}
