package com.ifconnect.ifconnectbackend.auth;

import com.ifconnect.ifconnectbackend.models.Usuario;
import com.ifconnect.ifconnectbackend.requestmodels.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AutheticationValidator {

    private static final String ERRO_DUPLICATE = "Detalhe: Key (%s)=(%s) already exists.";
    public static ResponseEntity<ErrorResponse> handleDuplicateError(String message, Usuario user) {
        String errorMessage = "";

        if (containsDuplicateError(message, "nome", user.getNome())) {
            errorMessage = "O usuário " + user.getNome() +" já existe.";
        } else if (containsDuplicateError(message, "email", user.getEmail())) {
            errorMessage = "O email "+ user.getEmail() +" fornecido já está em uso.";
        } else if (user.getAluno() != null && containsDuplicateError(message, "matricula", user.getAluno().getMatricula())) {
            errorMessage = "O aluno de matricula "+ user.getAluno().getMatricula() +" já foi cadastrado.";
        } else if (user.getProfessor() != null && containsDuplicateError(message, "siape", user.getProfessor().getSiape())) {
            errorMessage = "O siape "+ user.getProfessor().getSiape() +" fornecido já está em uso.";
        } else {
            errorMessage = "Erro ao processar a requisição.";
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.name()));
    }

    public static boolean containsDuplicateError(String message, String fieldName, String fieldValue) {
        return message.contains(String.format(ERRO_DUPLICATE, fieldName, fieldValue));
    }
}
