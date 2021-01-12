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

import br.com.frwk.Services.ModeloService;
import br.com.frwk.entity.PastasModelos;

@RestController
@RequestMapping("/engine/modelo")
public class ModeloEndpoints {

	@Autowired
	ModeloService modeloService;

	@GetMapping
	public List<PastasModelos> getModelo() {
		return modeloService.getModelo();
	}

	@GetMapping("/{id}")
	public Optional<PastasModelos> getModeloById(@PathVariable(value = "id") String id) {
		return modeloService.getModeloById(id);
	}

	@PostMapping
	public PastasModelos postModelo(@RequestBody PastasModelos objeto) {
		return modeloService.saveModelo(objeto);
	}

	@PutMapping
	public PastasModelos putModelo(@RequestBody PastasModelos objeto) {
		return modeloService.saveModelo(objeto);
	}

	@DeleteMapping
	public void deleteModelo(@RequestParam String id) {
		modeloService.deleteModelo(id);
	}
}
