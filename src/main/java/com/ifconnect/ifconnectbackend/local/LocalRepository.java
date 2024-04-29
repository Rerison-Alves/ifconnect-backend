package com.ifconnect.ifconnectbackend.local;

import com.ifconnect.ifconnectbackend.models.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LocalRepository extends JpaRepository<Local, Integer> {
    @Query("FROM Local l " +
            "WHERE :searchTerm is null or LOWER(l.nome) like %:searchTerm% " +
            "OR cast(l.id as string) = :searchTerm")
    Page<Local> searchPageable(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT l FROM Local l" +
            " WHERE l NOT IN " +
            "(SELECT a.local FROM Encontro e JOIN e.agendamento a " +
                "WHERE (:startDateTime BETWEEN a.startTime AND a.endTime) " +
                "OR (:endDateTime BETWEEN a.startTime AND a.endTime))")
    List<Local> findAvailableLocais(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
