package br.edu.ifce.springclassroomapi.domain.entities;

import br.edu.ifce.springclassroomapi.domain.enums.CursoTipo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso extends EntityBase {
    private String nome;
    @Enumerated(EnumType.STRING)
    private CursoTipo cursoTipo;

    protected Curso() {
    }

    public Curso(String nome, CursoTipo cursoTipo) {
        this.nome = nome;
        this.cursoTipo = cursoTipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CursoTipo getCursoTipo() {
        return cursoTipo;
    }

    public void setCursoTipo(CursoTipo cursoTipo) {
        this.cursoTipo = cursoTipo;
    }
}
