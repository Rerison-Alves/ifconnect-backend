package com.ifconnect.ifconnectbackend.mailcode;

import com.ifconnect.ifconnectbackend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Integer> {
    Optional<Code> findByValueAndUsuario(int value, Usuario usuario);

    List<Code> findByExpiredFalse();
}