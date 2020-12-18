package br.com.frwk.Services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.frwk.JpaRepository.RepositoryRegras;
import br.com.frwk.entity.Regras;

@Service
public class RegraService {

	@Autowired
	RepositoryRegras repository;

	public List<Regras> getRegras() {
		return repository.findAll();
	}
	public Optional<Regras> getRegrasById(String id){
		return repository.findById(id);

	}

	public Regras saveRegras(Regras objeto) {
		return repository.saveAndFlush(objeto);
	}

	public void deleteRegras(String id) {
		repository.deleteById(id);
	}
	public JsonObject getRegraSchema(String id) {
		Optional<Regras> regra = getRegrasById(id);
		return new Gson().fromJson(regra.get().getSchemaregras(), JsonObject.class);
	}
}
