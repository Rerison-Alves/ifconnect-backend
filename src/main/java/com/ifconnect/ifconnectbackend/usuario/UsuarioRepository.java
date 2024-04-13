package com.ifconnect.ifconnectbackend.usuario;

import com.ifconnect.ifconnectbackend.models.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

  Optional<Usuario> findByEmail(String email);

  @Transactional
  @Modifying
  @Query("UPDATE Usuario a " +
          "SET a.enabled = TRUE WHERE a.email = ?1")
  void enableUsuario(String email);

  @Query("FROM Usuario c " +
          "WHERE :searchTerm is null or LOWER(c.nome) like %:searchTerm% " +
          "OR :searchTerm is null or LOWER(c.email) like %:searchTerm% " +
          "OR cast(c.id as string) = :searchTerm")
  Page<Usuario> searchPageable(@Param("searchTerm") String searchTerm, Pageable pageable);
}
