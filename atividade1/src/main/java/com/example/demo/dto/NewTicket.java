package com.example.demo.dto;

import com.example.demo.model.entity.Ticket;

public record NewTicket(
		String acao,
		String objeto,
		String detalhamento,
		Integer user,
		Ticket.StatusType status
		) {
}
