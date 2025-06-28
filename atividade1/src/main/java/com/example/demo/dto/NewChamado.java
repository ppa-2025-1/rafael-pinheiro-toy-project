package com.example.demo.dto;

import com.example.demo.model.entity.Chamado;

public record NewChamado(
		String acao,
		String objeto,
		String detalhamento,
		Integer user,
		Chamado.StatusType status
		) {
}
