package com.example.demo.dto;

import com.example.demo.model.entity.Ticket;

public record TicketResponse(
		String acao,
		String objeto,
		String detalhamento,
		String user,
		Ticket.StatusType status) {
}
