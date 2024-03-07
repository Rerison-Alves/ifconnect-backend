package com.ifconnect.ifconnectbackend.curso;

import com.ifconnect.ifconnectbackend.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
