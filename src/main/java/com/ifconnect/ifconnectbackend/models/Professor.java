package com.ifconnect.ifconnectbackend.models;

import com.ifconnect.ifconnectbackend.models.enums.SituacaoProfessor;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
public class Professor extends Usuario {

    @Getter
    @Column(unique = true)
    private String siape;

    @Enumerated(EnumType.STRING)
    private SituacaoProfessor situacao;
}
