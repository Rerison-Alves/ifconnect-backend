package com.ifconnect.ifconnectbackend.agrupamento;

import com.ifconnect.ifconnectbackend.grupo.GrupoRepository;
import com.ifconnect.ifconnectbackend.models.Agrupamento;
import com.ifconnect.ifconnectbackend.models.Grupo;
import com.ifconnect.ifconnectbackend.models.Turma;
import com.ifconnect.ifconnectbackend.models.modelvo.SearchFilter;
import com.ifconnect.ifconnectbackend.turma.TurmaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgrupamentoService {

    private final GrupoRepository grupoRepository;

    private final TurmaRepository turmaRepository;

    @Transactional()
    public Page<Agrupamento> agrupamentosPageable(Integer userId, Integer cursoId, SearchFilter searchFilter) {
        // Pageable para trazer todos os registros
        Pageable pageableAll = PageRequest.of(0, Integer.MAX_VALUE, searchFilter.getDirection(), searchFilter.getOrder());

        // Buscar todos os grupos e turmas
        Page<Grupo> grupos = grupoRepository.searchPageable(userId, cursoId, searchFilter.getFilter().get(), pageableAll);
        Page<Turma> turmas = turmaRepository.searchPageable(userId, cursoId, searchFilter.getFilter().get(), pageableAll);

        // Combinar os resultados
        List<Agrupamento> combinedList = new ArrayList<>();
        combinedList.addAll(grupos.getContent());
        combinedList.addAll(turmas.getContent());

        // Ordenar a lista combinada (se necessário)
        combinedList.sort(Comparator.comparing(Agrupamento::getNome));

        // Aplicar paginação manualmente
        Pageable pageable = PageRequest.of(searchFilter.getPage(), searchFilter.getSize(), searchFilter.getDirection(), searchFilter.getOrder());
        int start = Math.min((int) pageable.getOffset(), combinedList.size());
        int end = Math.min((start + pageable.getPageSize()), combinedList.size());
        List<Agrupamento> paginatedList = combinedList.subList(start, end);

        return new PageImpl<>(paginatedList, pageable, combinedList.size());
    }

}
