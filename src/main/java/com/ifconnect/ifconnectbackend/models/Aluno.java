package com.ifconnect.ifconnectbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Aluno {

    @ManyToOne
    @JoinColumn(name = "id_curso")
    @JsonIgnoreProperties("iconeBase64")
    private Curso curso;

    @Column(unique = true)
    @NotBlank(message = "Matrícula ${notblank}")
    @Size(min = 14, max = 14, message = "Matrícula ${exactsize}")
    private String matricula;

}
