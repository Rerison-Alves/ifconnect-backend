package com.ifconnect.ifconnectbackend.curso;

import com.ifconnect.ifconnectbackend.models.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
    @Query("FROM Curso c " +
            "WHERE :searchTerm is null or LOWER(c.descricao) like %:searchTerm% " +
            "OR cast(c.id as string) = :searchTerm")
    Page<Curso> searchPageable(@Param("searchTerm") String searchTerm, Pageable pageable);
}
