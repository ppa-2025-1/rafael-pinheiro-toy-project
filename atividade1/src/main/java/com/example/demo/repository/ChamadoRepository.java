package com.example.demo.repository;

import java.util.Optional;
import com.example.demo.model.entity.Chamado;

public interface ChamadoRepository extends BaseRepository<Chamado, Integer> {

    Optional<Chamado> findByAcao(String acao);

    boolean existsByAcao(String acao);

}
