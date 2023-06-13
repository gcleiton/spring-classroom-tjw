package br.edu.ifce.springclassroomapi.infrastructure.repositories;

import br.edu.ifce.springclassroomapi.domain.entities.Identificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface IdentificacaoRepository extends JpaRepository<Identificacao, UUID> {
}
