package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "encontros")
public class Encontro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tema ${notblank}")
    @Size(min = 3, max = 100, message = "${size}")
    private String tema;

    @NotBlank(message = "Descrição ${notblank}")
    @Size(max = 255, message = "Descrição ${maxsize}")
    private String descricao;

    @Embedded
    private Agendamento agendamento;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private Turma turma;
}
