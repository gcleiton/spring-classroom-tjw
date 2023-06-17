package br.edu.ifce.springclassroomapi.application.dtos.usuario;

import br.edu.ifce.springclassroomapi.domain.entities.Usuario;
import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ListUsuarioDTO {
    private final UUID cursoId;
    private final IdentificacaoTipo identificacaoTipo;

    public ListUsuarioDTO(UUID cursoId, IdentificacaoTipo identificacaoTipo) {
        this.cursoId = cursoId;
        this.identificacaoTipo = identificacaoTipo;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public IdentificacaoTipo getIdentificacaoTipo() {
        return identificacaoTipo;
    }

    public Specification<Usuario> toSpecification() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                cursoId != null ? criteriaBuilder.equal(root.get("curso").get("id"), cursoId) : criteriaBuilder.conjunction(),
                identificacaoTipo != null ? criteriaBuilder.equal(root.get("identificacaoTipo"), identificacaoTipo) : criteriaBuilder.conjunction());
    }
}
