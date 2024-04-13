package com.ifconnect.ifconnectbackend.econtro;

import com.ifconnect.ifconnectbackend.models.Encontro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EncontroRepository extends JpaRepository<Encontro, Integer> {
    @Query("FROM Encontro e " +
            "WHERE :searchTerm is null " +
            "OR LOWER(e.tema) like %:searchTerm% " +
            "OR LOWER(e.descricao) like %:searchTerm% " +
            "OR cast(e.id as string) = :searchTerm")
    Page<Encontro> searchPageable(@Param("searchTerm") String searchTerm, Pageable pageable);

    List<Encontro> findByGrupo_Id(Integer id);

    List<Encontro> findByTurma_Id(Integer id);
}
