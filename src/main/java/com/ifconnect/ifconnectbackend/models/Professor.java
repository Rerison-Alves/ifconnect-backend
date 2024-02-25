package com.ifconnect.ifconnectbackend.models;

import com.ifconnect.ifconnectbackend.models.enums.SituacaoProfessor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Professor extends User {

    private String siape;

    @Enumerated(EnumType.STRING)
    private SituacaoProfessor situacao;
}
