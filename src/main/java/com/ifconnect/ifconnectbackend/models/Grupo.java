package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

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
    private List<Usuario> usuarios;

}