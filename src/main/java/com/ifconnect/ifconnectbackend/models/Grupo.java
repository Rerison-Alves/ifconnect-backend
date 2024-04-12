package com.ifconnect.ifconnectbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grupos")
public class Grupo extends Agrupamento{

    @NotBlank(message = "Área de estudo ${notblank}")
    @Size(min = 3, max = 100, message = "${size}")
    private String areadeEstudo;

    @ManyToMany(mappedBy = "grupos", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<Usuario> usuarios;

}