package br.edu.ifce.springclassroomapi.application.viewmodels.usuario;

import br.edu.ifce.springclassroomapi.application.viewmodels.ViewModelWithIdAndNome;
import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import br.edu.ifce.springclassroomapi.domain.enums.Situacao;

import java.util.UUID;

public class UsuarioIdentificacaoViewModel {
    private final UUID id;
    private final long numero;
    private final IdentificacaoTipo tipo;
    private final Situacao situacao;
    private final ViewModelWithIdAndNome curso;

    public UsuarioIdentificacaoViewModel(UUID id, long numero, IdentificacaoTipo tipo, Situacao situacao, ViewModelWithIdAndNome curso)
    {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.situacao = situacao;
        this.curso = curso;
    }

    public UUID getId() {
        return id;
    }

    public long getNumero() {
        return numero;
    }

    public IdentificacaoTipo getTipo() {
        return tipo;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public ViewModelWithIdAndNome getCurso() {
        return curso;
    }
}
