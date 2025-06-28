package com.example.demo.dto;

import com.example.demo.model.entity.Chamado;

public record ChamadoResponse(
		String acao,
		String objeto,
		String detalhamento,
		String user,
		Chamado.StatusType status) {
}
