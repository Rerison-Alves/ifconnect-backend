package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class Agrupamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotBlank(message = "${notblank}")
    @JoinColumn(name = "id_user", nullable = false)
    private Usuario admin;

    @Column(unique = true)
    @NotBlank(message = "${notblank}")
    @Size(min = 3, max = 100, message = "${size}")
    private String nome;

    private String descricao;

    @NotBlank(message = "${notblank}")
    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

}