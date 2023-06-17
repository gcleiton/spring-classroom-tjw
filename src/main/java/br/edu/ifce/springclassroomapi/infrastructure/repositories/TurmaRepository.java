package br.edu.ifce.springclassroomapi.infrastructure.repositories;

import br.edu.ifce.springclassroomapi.domain.entities.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, UUID> {
}
