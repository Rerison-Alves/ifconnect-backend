package com.ifconnect.ifconnectbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifconnect.ifconnectbackend.models.enums.TipoAgrupamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Getter
@Setter
@Indexed
@MappedSuperclass
public abstract class Agrupamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericField(sortable = Sortable.YES)
    private Integer id;

    @ManyToOne
    @NotNull(message = "Administador ${notblank}")
    @JoinColumn(name = "id_user", nullable = false)
    private Usuario admin;

    @Column(unique = true)
    @NotBlank(message = "Nome ${notblank}")
    @Size(min = 3, max = 100, message = "${size}")
    @FullTextField
    @GenericField(name = "nome_sort", sortable = Sortable.YES)
    private String nome;

    @Size(max = 255, message = "Descrição ${size}")
    @FullTextField
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    @JsonIgnoreProperties("iconeBase64")
    private Curso curso;

    @NotNull(message = "Tipo de agrupamento ${notblank}")
    @Enumerated(EnumType.STRING)
    private TipoAgrupamento tipoAgrupamento;

}