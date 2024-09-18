package com.ifconnect.ifconnectbackend.grupo;

import com.ifconnect.ifconnectbackend.models.Grupo;
import com.ifconnect.ifconnectbackend.models.modelvo.SearchFilter;
import io.vavr.control.Option;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.sort.dsl.SortOrder;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GrupoService {

    private final GrupoRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Grupo saveOrUpdate(Grupo entity) {
        return repository.saveAndFlush(entity);
    }

    public Grupo findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new NoResultException("Ops! Not Found entity for this id! :(")
        );
    }

    public List<Grupo> findAll() {
        return repository.findAll();
    }

    public Page<Grupo> gruposPageable(Integer userId, Integer cursoId, SearchFilter searchFilter) {
        return repository.searchPageable(userId, cursoId, searchFilter.getFilter().get(),
                PageRequest.of(searchFilter.getPage(), searchFilter.getSize(), searchFilter.getDirection(), searchFilter.getOrder()));
    }

    @Transactional
    public Page<Grupo> search(Integer userId, Integer cursoId, SearchFilter searchFilter) {
        SearchSession searchSession = Search.session(entityManager);

        // Montar e executar a query de busca
        SearchResult<Grupo> result = searchSession.search(Grupo.class)
                .where(f -> createQuery(f, searchFilter.getFilter(), userId, cursoId))
                .sort(f -> f.field(getSortField(searchFilter.getOrder()))
                        .order(getSortOrder(searchFilter.getDirection())))
                .fetch(searchFilter.getSize());

        // Configurar paginação e retorno
        PageRequest pageable = PageRequest.of(searchFilter.getPage(), searchFilter.getSize());
        return new PageImpl<>(result.hits(), pageable, result.total().hitCount());
    }

    public List<Grupo> findByAdmin(Integer id) {
        return repository.findByAdmin(id);
    }

    @Transactional
    public void delete(Grupo entity) {
        repository.delete(entity);
    }

    // Criar a query condicional
    private static <T> BooleanPredicateClausesStep<?> createQuery(
            SearchPredicateFactory f, Option<String> filter,
            Integer userId, Integer cursoId) {
        var bool = f.bool();
        filter.peek(flt -> bool.must(f.match().fields("nome", "descricao", "areadeEstudo").matching(flt)));
        if (cursoId != null) bool.must(f.match().field("curso.id").matching(cursoId));
        if (userId != null) bool.must(f.match().field("usuarios.id").matching(userId));
        return bool;
    }

    // Determinar o campo de ordenação
    private static String getSortField(String order) {
        return "nome".equals(order) ? "nome_sort" : "id";
    }

    private static SortOrder getSortOrder(Sort.Direction direction) {
        return direction == Sort.Direction.DESC ? SortOrder.DESC : SortOrder.ASC;
    }
}
