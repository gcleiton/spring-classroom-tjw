package br.edu.ifce.springclassroomapi.application.dtos.usuario;

import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import br.edu.ifce.springclassroomapi.domain.enums.Situacao;

import java.util.UUID;

public class UsuarioIdentificacaoDTO {
    private final long numero;
    private final IdentificacaoTipo tipo;
    private final Situacao situacao;
    private final UUID cursoId;

    public UsuarioIdentificacaoDTO(long numero, IdentificacaoTipo tipo, Situacao situacao, UUID cursoId) {
        this.numero = numero;
        this.tipo = tipo;
        this.situacao = situacao;
        this.cursoId = cursoId;
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

    public UUID getCursoId() {
        return cursoId;
    }
}
