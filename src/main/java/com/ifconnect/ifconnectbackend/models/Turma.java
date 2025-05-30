package com.ifconnect.ifconnectbackend.models;

import com.ifconnect.ifconnectbackend.models.enums.Turno;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turmas")
public class Turma extends Agrupamento{

    @NotBlank(message = "${notblank}")
    @Size(min = 3, max = 100, message = "${size}")
    private String disciplina;

    @NotNull(message = "Turno ${notblank}")
    @Enumerated(EnumType.STRING)
    private Turno turno;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_turma",
            joinColumns = @JoinColumn(name = "id_turma"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    private Set<Usuario> usuarios;
}
