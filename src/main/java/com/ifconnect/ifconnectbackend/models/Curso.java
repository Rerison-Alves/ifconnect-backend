package com.ifconnect.ifconnectbackend.models;

import com.ifconnect.ifconnectbackend.models.enums.Turno;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Icone ${notblank}")
    @Column(columnDefinition = "TEXT")
    private String iconeBase64;

    @NotBlank(message = "Descrição ${notblank}")
    @Column(unique = true)
    @Size(min = 3, max = 100, message = "${size}")
    private String descricao;

    @Size(max = 255, message = "Observação ${maxsize}")
    private String observacao;

    @NotNull(message = "Status ${notblank}")
    private Boolean status;

    @NotNull(message = "Turno ${notblank}")
    @Enumerated(EnumType.STRING)
    private Turno turno;

}
