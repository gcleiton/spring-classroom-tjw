package br.edu.ifce.springclassroomapi.application.viewmodels.curso;

import br.edu.ifce.springclassroomapi.domain.enums.CursoTipo;

import java.util.UUID;

public class ListCursoViewModel {
    private final UUID id;
    private final String nome;
    private final CursoTipo cursoTipo;

    public ListCursoViewModel(UUID id, String nome, CursoTipo cursoTipo) {
        this.id = id;
        this.nome = nome;
        this.cursoTipo = cursoTipo;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public CursoTipo getCursoTipo() {
        return cursoTipo;
    }
}
