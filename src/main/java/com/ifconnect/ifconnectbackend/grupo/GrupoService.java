package com.ifconnect.ifconnectbackend.grupo;

import com.ifconnect.ifconnectbackend.models.Grupo;
import com.ifconnect.ifconnectbackend.models.modelvo.SearchFilter;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoService {

    @Autowired
    private final GrupoRepository repository;

    @Transactional
    public Grupo saveOrUpdate(Grupo entity) {
        return repository.saveAndFlush(entity);
    }

    public Grupo findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new NoResultException("Ops! Not Found entity for this id! :(");
        });
    }

    public List<Grupo> findAll() {
        return repository.findAll();
    }

    public Page<Grupo> gruposPageable(Integer userId, SearchFilter searchFilter) {
        return repository.searchPageable(userId, searchFilter.getFilter().get(),
                PageRequest.of(searchFilter.getPage(), searchFilter.getSize(), searchFilter.getDirection(), searchFilter.getOrder()));
    }

    public List<Grupo> findByAdmin(Integer id) {
        return repository.findByAdmin(id);
    }

    @Transactional
    public void delete(Grupo entity) {
        repository.delete(entity);
    }
}
