package br.edu.ifce.springclassroomapi.core.results;

import org.springframework.http.HttpStatus;

public class OkResult<T> extends Result<T> {
    public OkResult() {
        super(HttpStatus.OK);
    }

    public OkResult(T data) {
        super(HttpStatus.OK, data);
    }
}
