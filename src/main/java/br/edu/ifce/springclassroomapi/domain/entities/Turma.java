package br.edu.ifce.springclassroomapi.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "turmas")
public class Turma extends EntityBase {
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private int ano;
    @Column(nullable = false)
    private int semestre;
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    @ManyToOne
    @JoinColumn(name = "professor_responsavel_id", nullable = false)
    private Usuario professorResponsavel;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "turmas_alunos",
            joinColumns = { @JoinColumn(name = "turma_id") },
            inverseJoinColumns = { @JoinColumn(name = "aluno_id") }
    )
    private List<Usuario> alunos;

    protected Turma() { }

    public Turma(String nome, int ano, int semestre, Curso curso, Usuario professorResponsavel, List<Usuario> alunos) {
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
        this.professorResponsavel = professorResponsavel;
        this.alunos = alunos;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getProfessorResponsavel() {
        return professorResponsavel;
    }

    public void setProfessorResponsavel(Usuario professorResponsavel) {
        this.professorResponsavel = professorResponsavel;
    }

    public List<Usuario> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Usuario> alunos) {
        this.alunos = alunos;
    }
}
