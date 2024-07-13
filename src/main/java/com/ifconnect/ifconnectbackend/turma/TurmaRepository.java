package com.ifconnect.ifconnectbackend.turma;

import com.ifconnect.ifconnectbackend.models.Turma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {

    @Query("SELECT g FROM Turma g WHERE g.admin.id = :adminId")
    List<Turma> findByAdmin(Integer adminId);

    @Query("SELECT DISTINCT t FROM Turma t JOIN t.usuarios u JOIN t.curso c " +
            "WHERE (:userId is null or u.id = :userId) " +
            "AND (:cursoId is null or c.id = :cursoId) " +
            "AND (:searchTerm is null or LOWER(t.nome) like %:searchTerm%) " +
            "OR cast(t.id as string) = :searchTerm")
    Page<Turma> searchPageable(@Param("userId") Integer userId,
                               @Param("cursoId") Integer cursoId,
                               @Param("searchTerm") String searchTerm,
                               Pageable pageable);
}
