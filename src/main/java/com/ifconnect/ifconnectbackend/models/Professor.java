package com.ifconnect.ifconnectbackend.models;

import com.ifconnect.ifconnectbackend.models.enums.SituacaoProfessor;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

@Embeddable
public class Professor {

    @Getter
    @Column(unique = true)
    @UniqueElements(message = "Siape ${unique}")
    @NotBlank(message = "Siape ${notblank}")
    @Size(max = 255, message = "Siape ${maxsize}")
    private String siape;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Situação ${notblank}")
    private SituacaoProfessor situacao;
}
