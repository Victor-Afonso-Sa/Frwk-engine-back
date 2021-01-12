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

import br.com.frwk.Services.PastaRegrasService;
import br.com.frwk.entity.PastaRegras;

@RestController
@RequestMapping("/engine/pastaregras")
public class PastaRegrasEndpoint {

	@Autowired
	PastaRegrasService PastaRegrasService;

	@GetMapping("/{id}")
	public Optional<PastaRegras> getPastaRegrasById(@PathVariable(value = "id") String id) {
		return PastaRegrasService.getPastaRegrasById(id);
	}

	@GetMapping
	public List<PastaRegras> getPastaRegras() {
		return PastaRegrasService.getPastaRegras();
	}

	@PostMapping
	public PastaRegras postPastaRegras(@RequestBody PastaRegras objeto) {
		return PastaRegrasService.savePastaRegras(objeto);
	}

	@PutMapping
	public PastaRegras putPastaRegras(@RequestBody PastaRegras objeto) {
		return PastaRegrasService.savePastaRegras(objeto);
	}

	@DeleteMapping
	public void deletePastaRegras(@RequestParam String id) {
		PastaRegrasService.deletePastaRegras(id);

	}
}