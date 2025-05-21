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

import com.example.demo.dto.NewTicket;
import com.example.demo.dto.TicketResponse;
import com.example.demo.model.business.TicketBusiness;
import com.example.demo.model.entity.Ticket;
import com.example.demo.repository.TicketRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController extends AbstractController {

	private final TicketRepository ticketRepository;
	private final TicketBusiness ticketBusiness;

	public TicketController(TicketRepository ticketRepository,
			TicketBusiness ticketBusiness) {
		this.ticketRepository = ticketRepository;
		this.ticketBusiness = ticketBusiness;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createNewTicket(
			@Valid @RequestBody NewTicket newTicket) {

		ticketBusiness.criarTicket(newTicket);

	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketResponse> getTicketById(@PathVariable Integer id) {
		Optional<Ticket> ticket = ticketRepository.findById(id);

		if (ticket.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		TicketResponse ticketResponse = new TicketResponse(
				ticket.get().getAcao(),
				ticket.get().getObjeto(),
				ticket.get().getDetalhamento(),
				ticket.get().getUser().getHandle(),
				ticket.get().getStatus());

		return ResponseEntity.ok(ticketResponse);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TicketResponse>> getTickets() {
		List<TicketResponse> ticketsResponse = ticketRepository.findAll()
				.stream()
				.map(ticket -> new TicketResponse(
						ticket.getAcao(),
						ticket.getObjeto(),
						ticket.getDetalhamento(),
						ticket.getUser().getHandle(),
						ticket.getStatus()))
				.toList();
		return ResponseEntity.ok(ticketsResponse);
	}

	@PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateTicket(
			@PathVariable Integer id,
			@Valid @RequestBody NewTicket newTicket) {

		if (!ticketRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		ticketBusiness.atualizarTicket(id, newTicket);

		return ResponseEntity.noContent().build();
	}
}
