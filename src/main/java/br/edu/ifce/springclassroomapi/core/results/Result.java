package br.edu.ifce.springclassroomapi.core.results;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Result<T> {
    private HttpStatus status;
    private T data;
    private Set<String> errors;
    private boolean IsFailure = false;

    public Result(HttpStatus status) {
        this.status = status;
        this.data = null;
        this.errors = new HashSet<>();
    }

    public Result(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
        this.errors = new HashSet<>();
    }

    public Result(HttpStatus status, String error) {
        this.status = status;
        this.data = null;
        this.errors = new HashSet<>(Collections.singletonList(error));
        this.IsFailure = true;
    }

    public Result(HttpStatus status, Set<String> errors) {
        this.status = status;
        this.data = null;
        this.errors = errors;
        this.IsFailure = true;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Set<String> getErrors() {
        return errors;
    }

    public void setErrors(Set<String> errors) {
        this.errors = errors;
    }

    public boolean isFailure() {
        return IsFailure;
    }
}
