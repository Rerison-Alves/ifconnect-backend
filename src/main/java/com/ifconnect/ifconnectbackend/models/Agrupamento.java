package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Agrupamento {

    @Column(unique = true)
    @NotBlank(message = "{descricao.notblank}")
    @Size(min = 3, max = 100, message = "{descricao.size}")
    private String nome;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

    @OneToMany
    private List<Encontro> encontros;

}