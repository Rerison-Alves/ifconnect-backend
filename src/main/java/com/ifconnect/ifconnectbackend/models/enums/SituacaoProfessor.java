package com.ifconnect.ifconnectbackend.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SituacaoProfessor {
    ATIVO("Ativo"),
    INATIVO("Inativo"),
    AFASTADO("Afastado"),
    LICENCA("Licença");

    private final String descricao;
}