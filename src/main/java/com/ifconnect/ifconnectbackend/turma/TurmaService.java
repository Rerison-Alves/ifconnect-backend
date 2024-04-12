package com.ifconnect.ifconnectbackend.turma;

import com.ifconnect.ifconnectbackend.models.Turma;
import com.ifconnect.ifconnectbackend.models.modelvo.SearchFilter;
import com.ifconnect.ifconnectbackend.usuario.UsuarioRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurmaService {

    private final TurmaRepository repository;

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Turma saveOrUpdate(Turma entity) {
        return repository.saveAndFlush(entity);
    }

    public Turma findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new NoResultException("Ops! Not Found entity for this id! :(")
        );
    }

    public List<Turma> findAll() {
        return repository.findAll();
    }

    public Page<Turma> turmasPageable(Integer userId, SearchFilter searchFilter) {
        return repository.searchPageable(userId, searchFilter.getFilter().get(),
                PageRequest.of(searchFilter.getPage(), searchFilter.getSize(), searchFilter.getDirection(), searchFilter.getOrder()));
    }

    public List<Turma> findByAdmin(Integer id) {
        return repository.findByAdmin(id);
    }

    @Transactional
    public void delete(Turma entity) {
        repository.delete(entity);
    }
}
