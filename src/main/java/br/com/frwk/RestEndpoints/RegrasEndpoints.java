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

import br.com.frwk.Services.RegraService;
import br.com.frwk.entity.Regras;

@RestController
@RequestMapping("/engine/regra")
public class RegrasEndpoints {

	@Autowired
	RegraService regraService;

	@GetMapping
	public List<Regras> getRegras() {
		return regraService.getRegras();
	}

	@GetMapping("/{id}")
	public Optional<Regras> getRegrasById(@PathVariable(value = "id") String id) {
		return regraService.getRegrasById(id);
	}

	@PostMapping
	public Regras postRegras(@RequestBody Regras objeto) {
		return regraService.saveRegras(objeto);
	}

	@PutMapping
	public Regras putRegras(@RequestBody Regras objeto) {
		return regraService.saveRegras(objeto);
	}

	@DeleteMapping()
	public void deleteRegras(@RequestParam String id) {
		regraService.deleteRegras(id);
	}
}
