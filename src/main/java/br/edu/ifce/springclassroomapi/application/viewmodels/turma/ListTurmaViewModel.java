package br.edu.ifce.springclassroomapi.application.viewmodels.turma;

import br.edu.ifce.springclassroomapi.application.viewmodels.ViewModelWithIdAndNome;

import java.util.UUID;

public class ListTurmaViewModel {
    private final UUID id;
    private final String nome;
    private final int ano;
    private final int semestre;
    private final ViewModelWithIdAndNome curso;
    private final ViewModelWithIdAndNome professorResponsavel;
    private final int quantidadeAlunos;

    public ListTurmaViewModel(
        UUID id,
        String nome,
        int ano,
        int semestre,
        ViewModelWithIdAndNome curso,
        ViewModelWithIdAndNome professorResponsavel,
        int quantidadeAlunos)
    {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
        this.professorResponsavel = professorResponsavel;
        this.quantidadeAlunos = quantidadeAlunos;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getAno() {
        return ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public ViewModelWithIdAndNome getCurso() {
        return curso;
    }

    public ViewModelWithIdAndNome getProfessorResponsavel() {
        return professorResponsavel;
    }

    public int getQuantidadeAlunos() {
        return quantidadeAlunos;
    }
}
