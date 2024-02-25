package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
public class Aluno extends Usuario {

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

    @Getter
    @Column(unique = true)
    private String matricula;

}
