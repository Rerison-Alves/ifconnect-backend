package com.ifconnect.ifconnectbackend.models;

import com.ifconnect.ifconnectbackend.models.enums.SituacaoProfessor;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Embeddable
public class Professor {

    @Getter
    @Column(unique = true)
    @NotBlank(message = "${notblank}")
    private String siape;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "${notblank}")
    private SituacaoProfessor situacao;
}
