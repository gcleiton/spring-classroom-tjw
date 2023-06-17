package br.edu.ifce.springclassroomapi.application.specifications;

import br.edu.ifce.springclassroomapi.domain.entities.Usuario;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "curso.id", params = "cursoId", spec = Equal.class),
        @Spec(path = "identificacaoTipo", params = "identificacaoTipo", spec = Equal.class)
})
public interface UsuarioSpecification extends Specification<Usuario> {
}
