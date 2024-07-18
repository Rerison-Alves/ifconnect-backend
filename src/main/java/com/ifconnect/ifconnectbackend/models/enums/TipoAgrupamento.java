package com.ifconnect.ifconnectbackend.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoAgrupamento {
    GRUPO("Grupo"),
    TURMA("Turma");

    private final String tipo;

}
