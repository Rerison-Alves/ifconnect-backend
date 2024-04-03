package com.ifconnect.ifconnectbackend.models;

import com.ifconnect.ifconnectbackend.models.enums.Turno;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
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
    private Turno turno;

    @ManyToMany(mappedBy = "turmas", fetch = FetchType.LAZY)
    private Set<Usuario> usuarios;
}
