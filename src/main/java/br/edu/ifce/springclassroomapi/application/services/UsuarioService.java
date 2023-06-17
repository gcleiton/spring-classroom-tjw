package br.edu.ifce.springclassroomapi.application.services;

import br.edu.ifce.springclassroomapi.application.dtos.usuario.UsuarioDTO;
import br.edu.ifce.springclassroomapi.application.viewmodels.ViewModelWithIdAndNome;
import br.edu.ifce.springclassroomapi.application.viewmodels.usuario.ListUsuarioViewModel;
import br.edu.ifce.springclassroomapi.core.results.*;
import br.edu.ifce.springclassroomapi.domain.entities.Curso;
import br.edu.ifce.springclassroomapi.domain.entities.Usuario;
import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import br.edu.ifce.springclassroomapi.infrastructure.repositories.CursoRepository;
import br.edu.ifce.springclassroomapi.infrastructure.repositories.TurmaRepository;
import br.edu.ifce.springclassroomapi.infrastructure.repositories.UsuarioRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final TurmaRepository identificacaoRepository;
    private final Validator validator;
    private final Sort defaultSort = Sort.by(Sort.Direction.ASC, "nome");

    public UsuarioService(
        UsuarioRepository usuarioRepository,
        CursoRepository cursoRepository,
        TurmaRepository identificacaoRepository,
        Validator validator)
    {
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.identificacaoRepository = identificacaoRepository;
        this.validator = validator;
    }

    public Result<List<ListUsuarioViewModel>> list() {
        var usuarios = usuarioRepository.findAll(defaultSort);
        var usuariosViewModel = usuarios.stream().map(usuario -> new ListUsuarioViewModel(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getIdentificacao(),
                usuario.getIdentificacaoTipo(),
                usuario.getCurso() != null ? new ViewModelWithIdAndNome(usuario.getCurso().getId(), usuario.getCurso().getNome()) : null
        )).collect(Collectors.toList());

        return new OkResult<>(usuariosViewModel);
    }

    public Result<List<ListUsuarioViewModel>> list(Specification<Usuario> specification) {
        var usuarios = usuarioRepository.findAll(specification, defaultSort);
        var usuariosViewModel = usuarios.stream().map(usuario -> new ListUsuarioViewModel(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getIdentificacao(),
                usuario.getIdentificacaoTipo(),
                usuario.getCurso() != null ? new ViewModelWithIdAndNome(usuario.getCurso().getId(), usuario.getCurso().getNome()) : null
        )).collect(Collectors.toList());

        return new OkResult<>(usuariosViewModel);
    }

    public Result<List<ViewModelWithIdAndNome>> listProfessores() {
        var professores = usuarioRepository.findByIdentificacaoTipo(IdentificacaoTipo.Professor);
        var professoresViewModel = professores.stream().map(professor -> new ViewModelWithIdAndNome(
                professor.getId(),
                professor.getNome()
        )).collect(Collectors.toList());

        return new OkResult<>(professoresViewModel);
    }

    public Result<List<ListUsuarioViewModel>> listAlunosByCursoId(UUID cursoId) {
        var alunos = usuarioRepository.findByCursoIdAndIdentificacaoTipo(cursoId, IdentificacaoTipo.Aluno);
        var alunosViewModel = alunos.stream().map(aluno -> new ListUsuarioViewModel(
            aluno.getId(),
            aluno.getNome(),
            aluno.getEmail(),
            aluno.getTelefone(),
            aluno.getIdentificacao(),
            aluno.getIdentificacaoTipo(),
            aluno.getCurso() != null ? new ViewModelWithIdAndNome(aluno.getCurso().getId(), aluno.getCurso().getNome()) : null
        )).collect(Collectors.toList());

        return new OkResult<>(alunosViewModel);
    }

    public Result<UsuarioDTO> loadById(UUID id) {
        var usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return new NotFoundResult<>("Usuário não encontrado");
        }

        var usuarioEntity = usuario.get();
        var usuarioDto = new UsuarioDTO(
            usuarioEntity.getId(),
            usuarioEntity.getNome(),
            usuarioEntity.getEmail(),
            usuarioEntity.getCpf(),
            usuarioEntity.getTelefone(),
            usuarioEntity.getIdentificacao(),
            usuarioEntity.getIdentificacaoTipo(),
            usuarioEntity.getCurso() != null ? usuarioEntity.getCurso().getId() : null
        );

        return new OkResult<>(usuarioDto);
    }

    public HashSet<String> ValidateUniqueness(UsuarioDTO request, UUID exceptId)
    {
        var conflictErrors = new HashSet<String>();
        var emailExists = exceptId == null
                ? usuarioRepository.existsByEmail(request.getEmail())
                : usuarioRepository.existsByEmail(request.getEmail(), exceptId);
        if (emailExists) {
            conflictErrors.add("Email já cadastrado");
        }

        var cpfExists = exceptId == null
                ? usuarioRepository.existsByCpf(request.getCpf())
                : usuarioRepository.existsByCpf(request.getCpf(), exceptId);
        if (cpfExists) {
            conflictErrors.add("CPF já cadastrado");
        }

        var identificacaoExists = exceptId == null
                ? usuarioRepository.existsByIdentificacao(request.getIdentificacao())
                : usuarioRepository.existsByIdentificacao(request.getIdentificacao(), exceptId);
        if (identificacaoExists) {
            conflictErrors.add("Identificação já cadastrada");
        }

        return conflictErrors;
    }

    public Result<UUID> add(UsuarioDTO request) {
        var errors = validator.validate(request);
        if (!errors.isEmpty()) {
            return new UnprocessableEntityResult<>(errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
        }

        var conflictErrors = ValidateUniqueness(request, null);
        if(!conflictErrors.isEmpty()) {
            return new UnprocessableEntityResult<>(conflictErrors);
        }

        Curso curso = null;
        if(request.getIdentificacaoTipo() == IdentificacaoTipo.Aluno) {
            var cursoFromRepository = cursoRepository.findById(request.getCursoId());
            if (cursoFromRepository.isEmpty()) {
                return new NotFoundResult<>("Curso não encontrado");
            }

            curso = cursoFromRepository.get();
        }

        var usuario = new Usuario(
            request.getNome(),
            request.getEmail(),
            request.getCpf(),
            request.getTelefone(),
            request.getIdentificacao(),
            request.getIdentificacaoTipo(),
            curso
        );

        usuarioRepository.save(usuario);

        return new CreatedResult<>(usuario.getId());
    }

    public Result update(UUID id, UsuarioDTO request) {
        var errors = validator.validate(request);
        if (!errors.isEmpty()) {
            return new UnprocessableEntityResult<>(errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
        }

        var usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return new NotFoundResult<>("Usuário não encontrado");
        }
        var usuarioEntity = usuario.get();

        var conflictErrors = ValidateUniqueness(request, usuarioEntity.getId());
        if(!conflictErrors.isEmpty()) {
            return new UnprocessableEntityResult<>(conflictErrors);
        }

        Curso curso = null;
        if(request.getIdentificacaoTipo() == IdentificacaoTipo.Aluno) {
            var cursoFromRepository = cursoRepository.findById(request.getCursoId());
            if (cursoFromRepository.isEmpty()) {
                return new NotFoundResult<>("Curso não encontrado");
            }

            curso = cursoFromRepository.get();
        }

        usuarioEntity.setNome(request.getNome());
        usuarioEntity.setEmail(request.getEmail());
        usuarioEntity.setCpf(request.getCpf());
        usuarioEntity.setTelefone(request.getTelefone());
        usuarioEntity.setIdentificacao(request.getIdentificacao());
        usuarioEntity.setIdentificacaoTipo(request.getIdentificacaoTipo());
        usuarioEntity.setCurso(curso);

        usuarioRepository.save(usuarioEntity);

        return new NoContentResult();
    }

    public Result remove(UUID id) {
        var usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return new NotFoundResult<>("Usuário não encontrado");
        }

        usuarioRepository.delete(usuario.get());

        return new NoContentResult();
    }
}
