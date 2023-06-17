package br.edu.ifce.springclassroomapi.infrastructure.repositories;

import br.edu.ifce.springclassroomapi.domain.entities.Usuario;
import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>, JpaSpecificationExecutor<Usuario> {
    boolean existsByEmail(String email);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u WHERE u.email = :email AND u.id <> :id")
    boolean existsByEmail(@Param("email") String email, @Param("id") UUID ignoreId);
    boolean existsByCpf(String cpf);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u WHERE u.cpf = :cpf AND u.id <> :id")
    boolean existsByCpf(@Param("cpf") String cpf, @Param("id") UUID ignoreId);
    boolean existsByIdentificacao(String identificacao);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u WHERE u.identificacao = :identificacao AND u.id <> :id")
    boolean existsByIdentificacao(@Param("identificacao") String identificacao, @Param("id") UUID ignoreId);
    boolean existsByIdAndIdentificacaoTipo(UUID id, IdentificacaoTipo identificacaoTipo);


    @Query("SELECT u FROM Usuario u WHERE u.identificacaoTipo = :identificacaoTipo")
    List<Usuario> findByIdentificacaoTipo(@Param("identificacaoTipo") IdentificacaoTipo identificacaoTipo);
    @Query("SELECT u FROM Usuario u WHERE u.identificacaoTipo = 'Aluno' AND u.curso.id = :cursoId")
    List<Usuario> findAlunosByCursoId(@Param("cursoId") UUID cursoId);

    List<Usuario> findByCursoIdAndIdentificacaoTipo(UUID cursoId, IdentificacaoTipo identificacaoTipo);

    @Query("SELECT u FROM Usuario u WHERE u.id IN :ids AND u.identificacaoTipo = :identificacaoTipo")
    List<Usuario> findByIdsAndIdentificacaoTipo(List<UUID> ids, IdentificacaoTipo identificacaoTipo);

    Optional<Usuario> findByIdAndIdentificacaoTipo(UUID id, IdentificacaoTipo identificacaoTipo);
}
