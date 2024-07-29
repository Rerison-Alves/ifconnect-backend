package com.ifconnect.ifconnectbackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifconnect.ifconnectbackend.models.enums.MessageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Encontro ${notblank}")
    @JoinColumn(name = "id_encontro", nullable = false)
    private Encontro encontro;

    @ManyToOne
    @NotNull(message = "Usuario ${notblank}")
    @JoinColumn(name = "id_user", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "${notblank}")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @Lob
    private String pdfBase64;

    @NotNull(message = "MessageType ${notblank}")
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private LocalDateTime data;

}