package br.edu.ifce.springclassroomapi.infrastructure.repositories;

import br.edu.ifce.springclassroomapi.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
