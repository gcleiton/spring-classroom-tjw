package br.edu.ifce.springclassroomapi.domain.entities;

import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import br.edu.ifce.springclassroomapi.domain.enums.Situacao;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "identificacoes")
public class Identificacao extends EntityBase {
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    private long numeroIdentificacao;
    private IdentificacaoTipo identificacaoTipo;
    private Situacao situacao;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    protected Identificacao() {}

    public Identificacao(
        Usuario usuario,
        long numeroIdentificacao,
        IdentificacaoTipo identificacaoTipo,
        Situacao situacaoTipo,
        Curso curso)
    {
        this.usuario = usuario;
        this.numeroIdentificacao = numeroIdentificacao;
        this.identificacaoTipo = identificacaoTipo;
        this.situacao = situacaoTipo;
        this.curso = curso;
    }

    public long getNumeroIdentificacao() {
        return numeroIdentificacao;
    }

    public IdentificacaoTipo getIdentificacaoTipo() {
        return identificacaoTipo;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public Curso getCurso() {
        return curso;
    }
}
