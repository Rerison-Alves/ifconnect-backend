package com.ifconnect.ifconnectbackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class Agendamento {
    @ManyToOne
    @JoinColumn(name = "id_local", referencedColumnName = "id")
    @JsonIgnoreProperties("iconeBase64")
    private Local local;

    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private LocalDateTime startTime;

    @Column(nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private LocalDateTime endTime;

}