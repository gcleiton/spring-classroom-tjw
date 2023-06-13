package br.edu.ifce.springclassroomapi.application.dtos.curso;

import br.edu.ifce.springclassroomapi.domain.enums.CursoTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCursoDTO {
    @NotBlank(message = "O nome do curso é obrigatório")
    private final String nome;

    @NotNull(message = "O tipo do curso é obrigatório")
    private final CursoTipo cursoTipo;

    public CreateCursoDTO(String nome, CursoTipo cursoTipo) {
        this.nome = nome;
        this.cursoTipo = cursoTipo;
    }

    public String getNome() {
        return nome;
    }
    public CursoTipo getCursoTipo() {
        return cursoTipo;
    }
}
