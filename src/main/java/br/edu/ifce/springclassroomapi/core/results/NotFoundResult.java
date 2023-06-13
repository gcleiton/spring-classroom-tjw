package br.edu.ifce.springclassroomapi.core.results;

import org.springframework.http.HttpStatus;

public class NotFoundResult<T> extends Result<T> {
    public NotFoundResult(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
