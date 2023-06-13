package br.edu.ifce.springclassroomapi.core.results;

import org.springframework.http.HttpStatus;

public class CreatedResult<T> extends Result<T> {
    public CreatedResult() {
        super(HttpStatus.CREATED);
    }

    public CreatedResult(T data) {
        super(HttpStatus.CREATED, data);
    }
}
