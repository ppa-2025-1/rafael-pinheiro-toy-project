package com.example.demo.model.business;

import java.time.LocalDateTime;

import com.example.demo.dto.NewChamado;
import com.example.demo.model.entity.Chamado;
import com.example.demo.model.entity.User;
import com.example.demo.repository.ChamadoRepository;
import com.example.demo.repository.UserRepository;

@Business
public class ChamadoBusiness {
	private ChamadoRepository chamadoRepository;
	private UserRepository userRepository;

	public ChamadoBusiness(ChamadoRepository chamadoRepository,
			UserRepository userRepository) {
		this.chamadoRepository = chamadoRepository;
		this.userRepository = userRepository;
	}

	public void criarChamado(NewChamado newChamado) {
		chamadoRepository.findByAcao(newChamado.acao())
				.ifPresent(chamado -> {
					throw new IllegalArgumentException("Chamado já existe");
				});

		userRepository.findById(newChamado.user())
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

		User user = new User();
		user.setId(newChamado.user());

		Chamado chamado = new Chamado();

		chamado.setAcao(newChamado.acao());
		chamado.setObjeto(newChamado.objeto());
		chamado.setDetalhamento(newChamado.detalhamento());
		chamado.setUser(user);
		chamado.setStatus(newChamado.status() != null ? newChamado.status() : Chamado.StatusType.NOVO);
		chamado.setCreatedAt(LocalDateTime.now());

		chamadoRepository.save(chamado);
	}

	public void atualizarChamado(Integer id, NewChamado newChamado) {
		Chamado chamado = chamadoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Chamado não encontrado"));

		User user = userRepository.findById(newChamado.user())
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

		chamado.setAcao(newChamado.acao());
		chamado.setObjeto(newChamado.objeto());
		chamado.setDetalhamento(newChamado.detalhamento());
		chamado.setUser(user);
		chamado.setStatus(newChamado.status() != null ? newChamado.status() : chamado.getStatus());
		chamado.setUpdatedAt(LocalDateTime.now());

		chamadoRepository.save(chamado);
	}
}
