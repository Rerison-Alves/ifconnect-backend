package com.ifconnect.ifconnectbackend.encontro;

import com.ifconnect.ifconnectbackend.models.Encontro;
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
public class EncontroService {

    private final EncontroRepository repository;

    @Transactional
    public Encontro saveOrUpdate(Encontro entity) {
        return repository.saveAndFlush(entity);
    }

    public Encontro findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new NoResultException("Ops! Not Found entity for this id! :(")
        );
    }

    public List<Encontro> findAll() {
        return repository.findAll();
    }

    public List<Encontro> findByGrupo(Integer idGrupo){
        return repository.findByGrupo_Id(idGrupo);
    }

    public List<Encontro> findByTurma(Integer idTurma){
        return repository.findByTurma_Id(idTurma);
    }

    public Page<Encontro> encontroPageable(SearchFilter searchFilter) {
        return repository.searchPageable(searchFilter.getFilter().get(),
                PageRequest.of(searchFilter.getPage(), searchFilter.getSize(), searchFilter.getDirection(), searchFilter.getOrder()));
    }

    @Transactional
    public void delete(Encontro entity) {
        repository.delete(entity);
    }
}
