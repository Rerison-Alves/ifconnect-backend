package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grupos")
public class Grupo extends Agrupamento{

    @NotBlank(message = "Área de estudo ${notblank}")
    @Size(min = 3, max = 100, message = "${size}")
    @FullTextField
    private String areadeEstudo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_grupo",
            joinColumns = @JoinColumn(name = "id_grupo"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    private Set<Usuario> usuarios;

}