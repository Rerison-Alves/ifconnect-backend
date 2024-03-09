package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Embeddable
public class Aluno extends Usuario {

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

    @Getter
    @Column(unique = true)
    @NotBlank(message = "${notblank}")
    private String matricula;

}
