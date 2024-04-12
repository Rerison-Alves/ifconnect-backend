package com.ifconnect.ifconnectbackend.curso;

import com.ifconnect.ifconnectbackend.models.Curso;
import com.ifconnect.ifconnectbackend.models.modelvo.SearchFilter;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Curso findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new NoResultException("Ops! Not Found entity for this id! :(")
        );
    }

    public List<Curso> findAll() {
        return repository.findAll();
    }

    public Page<Curso> cursoPageable(SearchFilter searchFilter) {
        return repository.searchPageable(searchFilter.getFilter().get(),
                PageRequest.of(searchFilter.getPage(), searchFilter.getSize(), searchFilter.getDirection(), searchFilter.getOrder()));
    }

    @Transactional
    public void delete(Curso entity) {
        repository.delete(entity);
    }
}
