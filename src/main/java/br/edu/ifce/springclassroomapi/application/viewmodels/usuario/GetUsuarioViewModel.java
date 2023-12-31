package br.edu.ifce.springclassroomapi.application.viewmodels.usuario;

import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;

import java.util.UUID;

public class GetUsuarioViewModel {
    private final UUID id;
    private final String nome;
    private final String email;
    private final String Cpf;
    private final String telefone;
    private final String identificacao;
    private final IdentificacaoTipo identificacaoTipo;
    private final UUID cursoId;

    public GetUsuarioViewModel(
        UUID id,
        String nome,
        String email,
        String cpf,
        String telefone,
        String identificacao,
        IdentificacaoTipo identificacaoTipo,
        UUID cursoId)
    {
        this.id = id;
        this.nome = nome;
        this.email = email;
        Cpf = cpf;
        this.telefone = telefone;
        this.identificacao = identificacao;
        this.identificacaoTipo = identificacaoTipo;
        this.cursoId = cursoId;
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

    public String getIdentificacao() {
        return identificacao;
    }

    public IdentificacaoTipo getIdentificacaoTipo() {
        return identificacaoTipo;
    }

    public UUID getCursoId() {
        return cursoId;
    }
}
