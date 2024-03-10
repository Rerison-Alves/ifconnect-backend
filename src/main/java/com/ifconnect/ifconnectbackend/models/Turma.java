package com.ifconnect.ifconnectbackend.models;

import com.ifconnect.ifconnectbackend.models.enums.Turno;
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
@Table(name = "turmas")
public class Turma extends Agrupamento{

    @NotBlank(message = "${notblank}")
    @Size(min = 3, max = 100, message = "${size}")
    private String disciplina;

    @NotBlank(message = "${notblank}")
    private Turno turno;

    @ManyToMany(mappedBy = "turmas", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;
}
