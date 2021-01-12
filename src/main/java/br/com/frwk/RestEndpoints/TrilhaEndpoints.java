package br.com.frwk.RestEndpoints;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.frwk.Services.TrilhaService;
import br.com.frwk.entity.Trilha;

@RestController
@RequestMapping("/engine/trilhas")
public class TrilhaEndpoints {

	@Autowired
	TrilhaService trilhasService;

	@GetMapping
	public List<Trilha> getAll() {
		return trilhasService.getAll();
	}

	@GetMapping("/{idregra}")
	public List<Trilha> getAllByIdRegra(@PathVariable(value = "idregra") String idregra) {
		return trilhasService.getAllByIdRegra(idregra);
	}

	@GetMapping("/idtrilha/{idtrilha}")
	public Optional<Trilha> getAllByIdRegra(@PathVariable(value = "idtrilha") Long idtrilha) {
		return trilhasService.getById(idtrilha);
	}

	@DeleteMapping
	public void delete(@RequestParam Long idtrilha) {
		trilhasService.deleteById(idtrilha);
	}
}
