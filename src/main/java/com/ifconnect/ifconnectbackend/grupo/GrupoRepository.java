package com.ifconnect.ifconnectbackend.grupo;

import com.ifconnect.ifconnectbackend.models.Grupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

    @Query("SELECT g FROM Grupo g WHERE g.admin.id = :adminId")
    List<Grupo> findByAdmin(Integer adminId);

    @Query("SELECT DISTINCT g FROM Grupo g JOIN g.usuarios u " +
            "WHERE (:userId is null or u.id = :userId) " +
            "AND (:searchTerm is null or LOWER(g.nome) like %:searchTerm%) " +
            "OR cast(g.id as string) = :searchTerm")
    Page<Grupo> searchPageable(@Param("userId") Integer userId, @Param("searchTerm") String searchTerm, Pageable pageable);
}
