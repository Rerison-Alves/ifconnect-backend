package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locais")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotBlank(message = "Nome ${notblank}")
    private String nome;

    @NotBlank(message = "${notblank}")
    private String localizacao;

    @Column(columnDefinition = "TEXT")
    private String iconeBase64;
}