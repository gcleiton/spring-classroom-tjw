package br.edu.ifce.springclassroomapi.core.results;

import org.springframework.http.HttpStatus;

public class NoContentResult extends Result {
    public NoContentResult() {
        super(HttpStatus.NO_CONTENT);
    }
}
