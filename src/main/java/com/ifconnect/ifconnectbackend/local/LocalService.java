package com.ifconnect.ifconnectbackend.local;

import com.ifconnect.ifconnectbackend.models.Local;
import com.ifconnect.ifconnectbackend.models.modelvo.SearchFilter;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository repository;

    @Transactional
    public Local saveOrUpdate(Local entity) {
        return repository.saveAndFlush(entity);
    }

    public Local findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new NoResultException("Ops! Not Found entity for this id! :(")
        );
    }

    public List<Local> findAll() {
        return repository.findAll();
    }

    public Page<Local> localPageable(SearchFilter searchFilter) {
        return repository.searchPageable(searchFilter.getFilter().get(),
                PageRequest.of(searchFilter.getPage(), searchFilter.getSize(), searchFilter.getDirection(), searchFilter.getOrder()));
    }

    @Transactional
    public List<Local> findAvailableByPeriod(String start, String end){
        try {
            LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            return repository.findAvailableLocais(startTime, endTime);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("Formato de data e hora inválido. Use o formato dd/MM/yyyy HH:mm");
        }
    }

    @Transactional
    public void delete(Local entity) {
        repository.delete(entity);
    }
}
