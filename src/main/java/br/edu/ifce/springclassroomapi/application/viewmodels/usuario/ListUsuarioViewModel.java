package br.edu.ifce.springclassroomapi.application.viewmodels.usuario;

import br.edu.ifce.springclassroomapi.application.viewmodels.ViewModelWithIdAndNome;
import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import com.google.gson.Gson;

import java.util.UUID;

public class ListUsuarioViewModel {
    private final UUID id;
    private final String nome;
    private final String email;
    private final String telefone;
    private final String identificacao;
    private final IdentificacaoTipo identificacaoTipo;
    private final ViewModelWithIdAndNome curso;

    public ListUsuarioViewModel(
        UUID id,
        String nome,
        String email,
        String telefone,
        String identificacao,
        IdentificacaoTipo identificacaoTipo,
        ViewModelWithIdAndNome curso)
    {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.identificacao = identificacao;
        this.identificacaoTipo = identificacaoTipo;
        this.curso = curso;
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

    public String getTelefone() {
        return telefone;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public IdentificacaoTipo getIdentificacaoTipo() {
        return identificacaoTipo;
    }

    public ViewModelWithIdAndNome getCurso() {
        return curso;
    }

    public String serialize() {
        var gson = new Gson();
        return gson.toJson(this);
    }
}
