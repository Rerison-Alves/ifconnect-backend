package com.ifconnect.ifconnectbackend.encontro;

import com.ifconnect.ifconnectbackend.models.Encontro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EncontroRepository extends JpaRepository<Encontro, Integer> {
    @Query("FROM Encontro e " +
            "WHERE :searchTerm is null " +
            "OR LOWER(e.tema) like %:searchTerm% " +
            "OR LOWER(e.descricao) like %:searchTerm% " +
            "OR cast(e.id as string) = :searchTerm")
    Page<Encontro> searchPageable(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT e FROM Encontro e " +
            "LEFT JOIN e.grupo g " +
            "LEFT JOIN e.turma t " +
            "LEFT JOIN g.usuarios gu " +
            "LEFT JOIN t.usuarios tu " +
            "WHERE (g IS NOT NULL AND gu.id = :userId OR t IS NOT NULL AND tu.id = :userId) " +
            "AND e.agendamento.endTime > :now " +
            "ORDER BY e.agendamento.startTime ASC")
    List<Encontro> findUpcomingEncontrosByUser_Id(@Param("userId") Integer userId, LocalDateTime now);

    List<Encontro> findByGrupo_Id(Integer id);

    @Query("SELECT e FROM Encontro e " +
            "WHERE e.grupo.id = :grupoId " +
            "AND e.agendamento.endTime > :now " +
            "ORDER BY e.agendamento.startTime ASC")
    List<Encontro> findUpcomingEncontrosByGrupo_Id(@Param("grupoId") Integer grupoId, LocalDateTime now);

    List<Encontro> findByTurma_Id(Integer id);

    @Query("SELECT e FROM Encontro e " +
            "WHERE e.turma.id = :turmaId " +
            "AND e.agendamento.endTime > :now " +
            "ORDER BY e.agendamento.startTime ASC")
    List<Encontro> findUpcomingEncontrosByTurma_Id(@Param("turmaId") Integer turmaId, LocalDateTime now);

}
