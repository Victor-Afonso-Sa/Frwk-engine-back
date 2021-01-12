package br.com.frwk.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.frwk.JpaRepository.RepositoryParametros;
import br.com.frwk.JpaRepository.RepositoryRegras;
import br.com.frwk.entity.Parametros;
import br.com.frwk.entity.Regras;
import br.com.frwk.entity.Trilha;

@Service
public class RegraService {

	@Autowired
	RepositoryRegras repository;
	@Autowired
	RepositoryParametros repositoryParametros;

	@Autowired
	TrilhaService trilhaService;

	@Value("${br.com.frwk.trilha}")
	private Boolean trilhaAtivada;

	public List<Regras> getRegras() {
		return repository.findAll();
	}

	public Optional<Regras> getRegrasById(String id) {
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

	public JsonArray getParametros() {
		JsonArray parametros = new JsonArray();
		List<Parametros> pastas = repositoryParametros.findAll();
		for (Integer index = 0; index < pastas.size(); index++) {
			String element = pastas.get(index).getParametros();
			JsonArray array = new Gson().fromJson(element, JsonArray.class);
			parametros.addAll(new Gson().fromJson(element, JsonArray.class));
		}
		return parametros;
	}

	public Trilha salvarTrilha(String id, JsonObject trilha) {
		if (trilhaAtivada) {
			Trilha trilhaExe = new Trilha(id, trilha.toString());
			return trilhaService.SalvarTrilha(trilhaExe);
		}
		return null;
	}
}
