package com.ifconnect.ifconnectbackend.models;

import com.ifconnect.ifconnectbackend.models.enums.Turno;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;

    private String observacao;

    private Boolean status;

    @Enumerated(EnumType.STRING)
    private Turno turno;

}
