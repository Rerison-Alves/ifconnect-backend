package com.ifconnect.ifconnectbackend.mensagem;

import com.ifconnect.ifconnectbackend.models.Mensagem;
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
public class MensagemService {

    @Autowired
    private MensagemRepository repository;

    @Transactional
    public Mensagem saveOrUpdate(Mensagem mensagem) {
        return repository.saveAndFlush(mensagem);
    }

    public Mensagem findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new NoResultException("Ops! Not Found entity for this id! :(")
        );
    }

    public List<Mensagem> findAll() {
        return repository.findAll();
    }

    public Page<Mensagem> mensagemPageable(Integer encontroId, SearchFilter searchFilter) {
        return repository.searchPageable(encontroId,
                PageRequest.of(searchFilter.getPage(), searchFilter.getSize(), searchFilter.getDirection(), searchFilter.getOrder()));
    }

    @Transactional
    public void delete(Mensagem entity) {
        repository.delete(entity);
    }
}
