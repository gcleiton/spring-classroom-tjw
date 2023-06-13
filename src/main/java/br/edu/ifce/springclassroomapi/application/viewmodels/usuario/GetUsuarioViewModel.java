package br.edu.ifce.springclassroomapi.application.viewmodels.usuario;

import java.util.List;
import java.util.UUID;

public class GetUsuarioViewModel {
    private final UUID id;
    private final String nome;
    private final String email;
    private final String Cpf;
    private final String telefone;
    private final List<GetUsuarioIdentificacaoViewModel> identificacoes;

    public GetUsuarioViewModel(
        UUID id,
        String nome,
        String email,
        String cpf,
        String telefone,
        List<GetUsuarioIdentificacaoViewModel> identificacoes)
    {
        this.id = id;
        this.nome = nome;
        this.email = email;
        Cpf = cpf;
        this.telefone = telefone;
        this.identificacoes = identificacoes;
    }


    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return Cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<GetUsuarioIdentificacaoViewModel> getIdentificacoes() {
        return identificacoes;
    }
}
