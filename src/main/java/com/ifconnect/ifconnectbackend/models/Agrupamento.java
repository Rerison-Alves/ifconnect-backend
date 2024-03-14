package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class Agrupamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull(message = "Administador ${notblank}")
    @JoinColumn(name = "id_user", nullable = false)
    private Usuario admin;

    @Column(unique = true)
    @UniqueElements(message = "Nome ${unique}")
    @NotBlank(message = "Nome ${notblank}")
    @Size(min = 3, max = 100, message = "${size}")
    private String nome;

    @Size(max = 255, message = "Descrição ${size}")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

}