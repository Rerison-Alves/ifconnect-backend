package com.ifconnect.ifconnectbackend.user;

import com.ifconnect.ifconnectbackend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Integer> {

  Optional<Usuario> findByEmail(String email);

}
