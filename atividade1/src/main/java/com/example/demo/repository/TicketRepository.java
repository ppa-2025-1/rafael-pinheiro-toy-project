package com.example.demo.repository;

import java.util.Optional;
import com.example.demo.model.entity.Ticket;

public interface TicketRepository extends BaseRepository<Ticket, Integer> {

    Optional<Ticket> findByAcao(String acao);

    boolean existsByAcao(String acao);

}
