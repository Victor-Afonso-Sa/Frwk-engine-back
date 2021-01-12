package br.com.frwk.RestEndpoints;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.frwk.Services.ParametrosService;
import br.com.frwk.entity.Parametros;

@RestController
@RequestMapping("/engine/parametros")
public class ParametrosEndpoints {

	@Autowired
	ParametrosService ParametrosService;

	@GetMapping("/{id}")
	public Optional<Parametros> getPastaRegrasById(@PathVariable(value = "id") String id) {
		return ParametrosService.getParamsById(id);
	}

	@GetMapping
	public List<Parametros> getPastaRegras() {
		return ParametrosService.getParams();
	}

	@PostMapping
	public Parametros postPastaRegras(@RequestBody Parametros objeto) {
		return ParametrosService.saveParams(objeto);
	}

	@PutMapping
	public Parametros putPastaRegras(@RequestBody Parametros objeto) {
		return ParametrosService.saveParams(objeto);
	}

	@DeleteMapping
	public void deletePastaRegras(@RequestParam String id) {
		ParametrosService.deleteParams(id);

	}
}
