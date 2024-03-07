package com.ifconnect.ifconnectbackend.curso;

import com.ifconnect.ifconnectbackend.models.Curso;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository repository;

    @Transactional
    public Curso saveOrUpdate(Curso entity) {
        return repository.saveAndFlush(entity);
    }

    @Transactional
    public Curso findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new NoResultException("Ops! Not Found entity for this id! :(");
        });
    }

    @Transactional
    public List<Curso> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void delete(Curso entity) {
        repository.delete(entity);
    }
}
