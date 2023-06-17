package br.edu.ifce.springclassroomapi.application.dtos.turma;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.util.Set;
import java.util.UUID;

public class TurmaDTO {
    private UUID id;
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @NotNull(message = "O ano é obrigatório")
    @Range(min = 1900, max = 2100, message = "Insira um ano válido")
    private int ano;
    @Range(min = 1, max = 2, message = "Insira um semestre válido")
    @NotNull(message = "O semestre é obrigatório")
    private int semestre;
    @NotNull(message = "O curso é obrigatório")
    private UUID cursoId;
    @NotNull(message = "O professor responsável é obrigatório")
    private UUID professorResponsavelId;
    private Set<UUID> alunosIds;

    public TurmaDTO() {
    }

    public TurmaDTO(String nome, UUID id, int ano, int semestre, UUID cursoId, UUID professorResponsavelId, Set<UUID> alunosIds) {
        this.nome = nome;
        this.id = id;
        this.ano = ano;
        this.semestre = semestre;
        this.cursoId = cursoId;
        this.professorResponsavelId = professorResponsavelId;
        this.alunosIds = alunosIds;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public UUID getProfessorResponsavelId() {
        return professorResponsavelId;
    }

    public void setProfessorResponsavelId(UUID professorResponsavelId) {
        this.professorResponsavelId = professorResponsavelId;
    }

    public Set<UUID> getAlunosIds() {
        return alunosIds;
    }

    public void setAlunosIds(Set<UUID> alunosIds) {
        this.alunosIds = alunosIds;
    }
}
