package com.ifconnect.ifconnectbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifconnect.ifconnectbackend.models.enums.TipoAgrupamento;
import com.ifconnect.ifconnectbackend.models.enums.Turno;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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
    @NotBlank(message = "Nome ${notblank}")
    @Size(min = 3, max = 100, message = "${size}")
    private String nome;

    @Size(max = 255, message = "Descrição ${size}")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    @JsonIgnoreProperties("iconeBase64")
    private Curso curso;

    @NotNull(message = "Tipo de agrupamento ${notblank}")
    @Enumerated(EnumType.STRING)
    private TipoAgrupamento tipoAgrupamento;

}