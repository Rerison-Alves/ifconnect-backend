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

    @Query("SELECT DISTINCT g FROM Turma g JOIN g.usuarios u " +
            "WHERE (:userId is null or u.id = :userId) " +
            "AND (:searchTerm is null or LOWER(g.nome) like %:searchTerm%) " +
            "OR cast(g.id as string) = :searchTerm")
    Page<Turma> searchPageable(@Param("userId") Integer userId, @Param("searchTerm") String searchTerm, Pageable pageable);
}