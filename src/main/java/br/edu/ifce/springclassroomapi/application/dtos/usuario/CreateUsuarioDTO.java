package br.edu.ifce.springclassroomapi.application.dtos.usuario;

import java.util.List;

public class CreateUsuarioDTO {
    private final String nome;
    private final String email;
    private final String cpf;
    private final String telefone;
    private final List<CreateUsuarioIdentificacaoDTO> identificacoes;

    public CreateUsuarioDTO(
        String nome,
        String email,
        String cpf,
        String telefone,
        List<CreateUsuarioIdentificacaoDTO> identificacoes)
    {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.identificacoes = identificacoes;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<CreateUsuarioIdentificacaoDTO> getIdentificacoes() {
        return identificacoes;
    }
}
