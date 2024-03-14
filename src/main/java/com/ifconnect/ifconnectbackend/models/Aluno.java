package com.ifconnect.ifconnectbackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

@Embeddable
public class Aluno {

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

    @Getter
    @Column(unique = true)
    @UniqueElements(message = "Matrícula ${unique}")
    @NotBlank(message = "Matrícula ${notblank}")
    @Size(min = 14, max = 14, message = "Matrícula ${exactsize}")
    private String matricula;

}
