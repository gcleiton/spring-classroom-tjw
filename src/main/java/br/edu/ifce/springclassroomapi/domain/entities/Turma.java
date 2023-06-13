package br.edu.ifce.springclassroomapi.domain.entities;

import java.util.List;
import java.util.UUID;

public class Turma extends EntityBase {
    private int ano;
    private int semestre;
    private UUID cursoId;
    private Curso curso;
    private UUID professorResponsavelId;
    private Usuario professorResponsavel;
    private List<Usuario> alunos;

    public Turma (int ano, int semestre, Curso curso, Usuario professorResponsavel) {

        this.ano = ano;
        this.semestre = semestre;
        cursoId = curso.getId();
        this.curso = curso;
        this.professorResponsavelId = professorResponsavel.getId();
        this.professorResponsavel = professorResponsavel;
    }
}
