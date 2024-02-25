package com.ifconnect.ifconnectbackend.requestmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    String mensagem;
    String tipo;

}
