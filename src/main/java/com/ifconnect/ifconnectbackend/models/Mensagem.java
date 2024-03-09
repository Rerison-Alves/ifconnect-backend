package com.ifconnect.ifconnectbackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mensagens")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotBlank(message = "${notblank}")
    @JoinColumn(name = "id_encontro", nullable = false)
    private Encontro encontro;

    @ManyToOne
    @NotBlank(message = "${notblank}")
    @JoinColumn(name = "id_user", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "${notblank}")
    @Column(nullable = false)
    @Lob
    private String texto;

    @Lob
    private String pdfBase64;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private LocalDateTime data;

}