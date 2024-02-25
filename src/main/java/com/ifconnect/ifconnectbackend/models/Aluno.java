package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Aluno extends User {

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

    private String matricula;

}
