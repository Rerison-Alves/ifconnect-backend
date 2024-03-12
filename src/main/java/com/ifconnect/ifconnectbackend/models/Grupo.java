package com.ifconnect.ifconnectbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grupos")
public class Grupo extends Agrupamento{

    @NotBlank(message = "${notblank}")
    @Size(min = 3, max = 100, message = "${size}")
    private String areadeEstudo;

    @ManyToMany(mappedBy = "grupos", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private Set<Usuario> usuarios;

}