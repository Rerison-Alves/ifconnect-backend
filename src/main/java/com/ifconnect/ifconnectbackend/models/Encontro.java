package com.ifconnect.ifconnectbackend.models;

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
@Table(name = "encontro")
public class Encontro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String tema;

    private String descricao;
}
