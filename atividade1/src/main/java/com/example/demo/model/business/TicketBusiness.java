package com.example.demo.model.business;

import java.time.LocalDateTime;

import com.example.demo.dto.NewTicket;
import com.example.demo.model.entity.Ticket;
import com.example.demo.model.entity.User;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;

@Business
public class TicketBusiness {
	private TicketRepository ticketRepository;
	private UserRepository userRepository;

	public TicketBusiness(TicketRepository ticketRepository,
			UserRepository userRepository) {
		this.ticketRepository = ticketRepository;
		this.userRepository = userRepository;
	}

	public void criarTicket(NewTicket newTicket) {
		ticketRepository.findByAcao(newTicket.acao())
				.ifPresent(ticket -> {
					throw new IllegalArgumentException("Ticket já existe");
				});

		userRepository.findById(newTicket.user())
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

		User user = new User();
		user.setId(newTicket.user());

		Ticket ticket = new Ticket();

		ticket.setAcao(newTicket.acao());
		ticket.setObjeto(newTicket.objeto());
		ticket.setDetalhamento(newTicket.detalhamento());
		ticket.setUser(user);
		ticket.setStatus(newTicket.status() != null ? newTicket.status() : Ticket.StatusType.NOVO);
		ticket.setCreatedAt(LocalDateTime.now());

		ticketRepository.save(ticket);
	}

	public void atualizarTicket(Integer id, NewTicket newTicket) {
		ticketRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado"));
		ticketRepository.findByAcao(newTicket.acao())
				.ifPresent(ticket -> {
					throw new IllegalArgumentException("Ticket já existe");
				});
		userRepository.findById(newTicket.user())
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
		User user = new User();
		user.setId(newTicket.user());
		Ticket ticket = new Ticket();
		ticket.setAcao(newTicket.acao());
		ticket.setObjeto(newTicket.objeto());
		ticket.setDetalhamento(newTicket.detalhamento());
		ticket.setUser(user);
		ticket.setStatus(newTicket.status() != null ? newTicket.status() : Ticket.StatusType.NOVO);
		ticket.setCreatedAt(LocalDateTime.now());
		ticket.setUpdatedAt(LocalDateTime.now());

		ticketRepository.save(ticket);
	}
}
