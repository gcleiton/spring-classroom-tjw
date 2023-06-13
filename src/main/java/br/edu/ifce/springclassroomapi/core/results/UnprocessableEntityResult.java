package br.edu.ifce.springclassroomapi.core.results;

import org.springframework.http.HttpStatus;

import java.util.Set;

public class UnprocessableEntityResult<T> extends Result<T> {
    public UnprocessableEntityResult() {
        super(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public UnprocessableEntityResult(Set<String> errors) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, errors);
    }
}
