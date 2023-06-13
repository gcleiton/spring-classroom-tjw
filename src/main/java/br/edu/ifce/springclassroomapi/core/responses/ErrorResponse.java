package br.edu.ifce.springclassroomapi.core.responses;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ErrorResponse {
    private final int statusCode;
    private final Set<String> errors;

    public ErrorResponse(HttpStatus statusCode, Set<String> errors) {
        this.statusCode = statusCode.value();
        this.errors = errors;
    }

    public ErrorResponse(HttpStatus statusCode, String error) {
        this.statusCode = statusCode.value();
        this.errors = new HashSet<>(Collections.singletonList(error));
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Set<String> getErrors() {
        return errors;
    }
}
