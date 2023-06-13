package br.edu.ifce.springclassroomapi.application.viewmodels;

import java.util.UUID;

public class ViewModelWithIdAndNome {
    private final UUID id;
    private final String nome;

    public ViewModelWithIdAndNome(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
