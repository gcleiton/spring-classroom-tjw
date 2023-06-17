package br.edu.ifce.springclassroomapi.application.viewmodels.turma;

import br.edu.ifce.springclassroomapi.application.viewmodels.ViewModelWithIdAndNome;
import br.edu.ifce.springclassroomapi.application.viewmodels.usuario.ListUsuarioViewModel;

import java.util.List;
import java.util.UUID;

public class GetTurmaViewModel {
    private final UUID id;
    private final String nome;
    private final int ano;
    private final int semestre;
    private final ViewModelWithIdAndNome curso;
    private final ViewModelWithIdAndNome professorResponsavel;
    private final List<ListUsuarioViewModel> alunos;

    public GetTurmaViewModel(
        UUID id,
        String nome,
        int ano,
        int semestre,
        ViewModelWithIdAndNome curso,
        ViewModelWithIdAndNome professorResponsavel,
        List<ListUsuarioViewModel> alunos)
    {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
        this.professorResponsavel = professorResponsavel;
        this.alunos = alunos;
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

    public List<ListUsuarioViewModel> getAlunos() {
        return alunos;
    }
}
