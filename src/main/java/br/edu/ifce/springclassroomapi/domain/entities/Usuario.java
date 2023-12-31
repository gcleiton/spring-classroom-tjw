package br.edu.ifce.springclassroomapi.domain.entities;

import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario extends EntityBase {
    @Column(nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String cpf;
    private String telefone;
    @Column(unique = true, nullable = false)
    private String identificacao;
    @Column(nullable = false)
    private IdentificacaoTipo identificacaoTipo;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToMany(mappedBy = "alunos")
    private List<Turma> turmas;

    protected Usuario() { }

    public Usuario(String nome, String email, String cpf, String telefone, String identificacao, IdentificacaoTipo identificacaoTipo, Curso curso) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.identificacao = identificacao;
        this.identificacaoTipo = identificacaoTipo;
        this.curso = curso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public IdentificacaoTipo getIdentificacaoTipo() {
        return identificacaoTipo;
    }

    public void setIdentificacaoTipo(IdentificacaoTipo identificacaoTipo) {
        this.identificacaoTipo = identificacaoTipo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
