package br.com.frwk.RestEndpoints;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.frwk.Services.ModeloService;
import br.com.frwk.Services.RegraService;
import br.com.frwk.Services.pastaRegrasService;
import br.com.frwk.entity.PastaRegras;
import br.com.frwk.entity.PastasModelos;
import br.com.frwk.entity.Regras;
import br.com.frwk.exeption.BreakExeption;
import br.com.frwk.exeption.RetornoExeption;
import br.com.frwk.motorregra.acao.impl.Regra;
import br.com.frwk.motorregra.util.ConstrutorRegra;
import br.com.frwk.motorregra.util.JavascriptExecutor;

@RestController
@RequestMapping("/engine")
public class Endpoints {

	@Autowired
	ModeloService modeloService;
	@Autowired
	RegraService regraService;
	
	@Autowired
	pastaRegrasService pastaRegrasService;
	
	@GetMapping("/modelo")
	public List<PastasModelos> getModelo() {
		return modeloService.getModelo();
	}

	@GetMapping("/modelo/{id}")
	public Optional<PastasModelos> getModeloById(@PathVariable(value = "id") String id) {
		return modeloService.getModeloById(id);
	}

	@PostMapping("/modelo")
	public PastasModelos postModelo(@RequestBody PastasModelos objeto) {
		return modeloService.saveModelo(objeto);
	}

	@PutMapping("/modelo")
	public PastasModelos putModelo(@RequestBody PastasModelos objeto) {
		return modeloService.saveModelo(objeto);
	}

	@DeleteMapping("/modelo")
	public void deleteModelo(@RequestParam String id) {
		modeloService.deleteModelo(id);
	}

	@GetMapping("/regra")
	public List<Regras> getRegras() {
		return regraService.getRegras();
	}

	@GetMapping("/regra/{id}")
	public Optional<Regras> getRegrasById(@PathVariable(value = "id") String id) {
		return regraService.getRegrasById(id);
	}

	@PostMapping("/regra")
	public Regras postRegras(@RequestBody Regras objeto) {
		return regraService.saveRegras(objeto);
	}

	@PutMapping("/regra")
	public Regras putRegras(@RequestBody Regras objeto) {
		return regraService.saveRegras(objeto);
	}

	@DeleteMapping("/regra")
	public void deleteRegras(@RequestParam String id) {
		regraService.deleteRegras(id);
	}

	@PostMapping("/run/{id}")
	public String run(@PathVariable(value = "id") String id, @RequestBody String entrada) throws ScriptException {
		JsonObject json = regraService.getRegraSchema(id);
		ConstrutorRegra construtor = new ConstrutorRegra(regraService);
		Regra regraExe = construtor.criarRegra(json);
		JavascriptExecutor executor = new JavascriptExecutor(regraExe.getVariaveis(), entrada);
		try {
			regraExe.executar(executor);
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (RetornoExeption e) {
			return executor.executarExpressao("saida");
		} catch (BreakExeption e) {}
		return null;
	}
	@GetMapping("/pastaregras/{id}")
	public Optional<PastaRegras> getPastaRegrasById(@PathVariable(value = "id") String id) {
		return pastaRegrasService.getPastaRegrasById(id);
	}
	@GetMapping("/pastaregras")
	public List<PastaRegras> getPastaRegras() {
		return pastaRegrasService.getPastaRegras();
	}

	@PostMapping("/pastaregras")
	public PastaRegras postPastaRegras(@RequestBody PastaRegras objeto) {
		return pastaRegrasService.savePastaRegras(objeto);
	}

	@PutMapping("/pastaregras")
	public PastaRegras putPastaRegras(@RequestBody PastaRegras objeto) {
		return pastaRegrasService.savePastaRegras(objeto);
	}

	@DeleteMapping("/pastaregras")
	public void deletePastaRegras(@RequestParam String id) {
		 pastaRegrasService.deletePastaRegras(id);
	}
}
