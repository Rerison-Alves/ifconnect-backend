package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turma")
public class Turma extends Agrupamento{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String disciplina;

    private String Turno;
}
