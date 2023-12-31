package br.edu.ifce.springclassroomapi.infrastructure.repositories;

import br.edu.ifce.springclassroomapi.domain.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CursoRepository extends JpaRepository<Curso, UUID> {

}
