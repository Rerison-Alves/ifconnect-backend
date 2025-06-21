package com.ifconnect.ifconnectbackend.mensagem;

import com.ifconnect.ifconnectbackend.models.Mensagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {
    @Query("FROM Mensagem m " +
            "WHERE :encontroId is null or m.encontro.id = :encontroId")
    Page<Mensagem> searchPageable(@Param("encontroId") Integer encontroId, Pageable pageable);
}