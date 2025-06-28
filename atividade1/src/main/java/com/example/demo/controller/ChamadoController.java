package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NewChamado;
import com.example.demo.dto.ChamadoResponse;
import com.example.demo.model.business.ChamadoBusiness;
import com.example.demo.model.entity.Chamado;
import com.example.demo.repository.ChamadoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/chamado")
public class ChamadoController extends AbstractController {

	private final ChamadoRepository chamadoRepository;
	private final ChamadoBusiness chamadoBusiness;

	public ChamadoController(ChamadoRepository chamadoRepository,
			ChamadoBusiness chamadoBusiness) {
		this.chamadoRepository = chamadoRepository;
		this.chamadoBusiness = chamadoBusiness;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createNewChamado(
			@Valid @RequestBody NewChamado newChamado) {

		chamadoBusiness.criarChamado(newChamado);

	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ChamadoResponse> getChamadoById(@PathVariable("id") Integer id) {
		Optional<Chamado> chamado = chamadoRepository.findById(id);

		if (chamado.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		ChamadoResponse chamadoResponse = new ChamadoResponse(
				chamado.get().getAcao(),
				chamado.get().getObjeto(),
				chamado.get().getDetalhamento(),
				chamado.get().getUser().getHandle(),
				chamado.get().getStatus());

		return ResponseEntity.ok(chamadoResponse);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChamadoResponse>> getChamados() {
		List<ChamadoResponse> chamadosResponse = chamadoRepository.findAll()
				.stream()
				.map(chamado -> new ChamadoResponse(
						chamado.getAcao(),
						chamado.getObjeto(),
						chamado.getDetalhamento(),
						chamado.getUser().getHandle(),
						chamado.getStatus()))
				.toList();
		return ResponseEntity.ok(chamadosResponse);
	}

	@PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateChamado(
			@PathVariable("id") Integer id,
			@Valid @RequestBody NewChamado newChamado) {

		if (!chamadoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		chamadoBusiness.atualizarChamado(id, newChamado);

		return ResponseEntity.noContent().build();
	}
}
