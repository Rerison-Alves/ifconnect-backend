package com.ifconnect.ifconnectbackend.models.enums;

import lombok.Getter;

@Getter
public enum Turno {
    MANHA("Manhã"),
    TARDE("Tarde"),
    NOITE("Noite");

    private final String descricao;

    Turno(String descricao) {
        this.descricao = descricao;
    }

}
