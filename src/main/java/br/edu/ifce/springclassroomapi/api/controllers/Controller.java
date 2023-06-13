package br.edu.ifce.springclassroomapi.api.controllers;

import br.edu.ifce.springclassroomapi.core.responses.ErrorResponse;
import br.edu.ifce.springclassroomapi.core.results.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class Controller {
    protected <T> ResponseEntity HandleResponse(Result<T> result) {
        return switch (result.getStatus()) {
            case OK, CREATED, NO_CONTENT -> ResponseEntity.status(result.getStatus()).body(result.getData());
            case NOT_FOUND, UNPROCESSABLE_ENTITY -> ResponseEntity.status(result.getStatus()).body(new ErrorResponse(result.getStatus(), result.getErrors()));
            default ->
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível tratar a resposta."));
        };
    }
}
