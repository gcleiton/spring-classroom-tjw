package br.edu.ifce.springclassroomapi.application.services;

import br.edu.ifce.springclassroomapi.application.dtos.usuario.CreateUsuarioDTO;
import br.edu.ifce.springclassroomapi.application.dtos.usuario.CreateUsuarioIdentificacaoDTO;
import br.edu.ifce.springclassroomapi.application.viewmodels.ViewModelWithIdAndNome;
import br.edu.ifce.springclassroomapi.application.viewmodels.usuario.GetUsuarioIdentificacaoViewModel;
import br.edu.ifce.springclassroomapi.application.viewmodels.usuario.GetUsuarioViewModel;
import br.edu.ifce.springclassroomapi.core.results.*;
import br.edu.ifce.springclassroomapi.domain.entities.Identificacao;
import br.edu.ifce.springclassroomapi.domain.entities.Usuario;
import br.edu.ifce.springclassroomapi.infrastructure.repositories.CursoRepository;
import br.edu.ifce.springclassroomapi.infrastructure.repositories.IdentificacaoRepository;
import br.edu.ifce.springclassroomapi.infrastructure.repositories.UsuarioRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final IdentificacaoRepository identificacaoRepository;
    private final Validator validator;

    public UsuarioService(
        UsuarioRepository usuarioRepository,
        CursoRepository cursoRepository,
        IdentificacaoRepository identificacaoRepository,
        Validator validator)
    {
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.identificacaoRepository = identificacaoRepository;
        this.validator = validator;
    }

//    public Result<List<ListCursoViewModel>> list() {
//        var cursos = usuarioRepository.findAll();
//        var cursosViewModel = cursos.stream().map(curso -> new ListCursoViewModel(
//                curso.getId(),
//                curso.getNome(),
//                curso.getCursoTipo()
//        )).collect(Collectors.toList());
//
//        return new OkResult<>(cursosViewModel);
//    }
//
    public Result<GetUsuarioViewModel> loadById(UUID id) {
        var usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return new NotFoundResult<>("Usuário não encontrado");
        }

        var usuarioEntity = usuario.get();
        var usuarioViewModel = new GetUsuarioViewModel(
            usuarioEntity.getId(),
            usuarioEntity.getNome(),
            usuarioEntity.getEmail(),
            usuarioEntity.getCpf(),
            usuarioEntity.getTelefone(),
            usuarioEntity.getIdentificacoes()
                .stream()
                .map(identificacao -> new GetUsuarioIdentificacaoViewModel(
                    identificacao.getId(),
                    identificacao.getNumeroIdentificacao(),
                    identificacao.getIdentificacaoTipo(),
                    identificacao.getSituacao(),
                    identificacao.getCurso() != null ? new ViewModelWithIdAndNome(identificacao.getCurso().getId(), identificacao.getCurso().getNome()) : null
                ))
                .collect(Collectors.toList())
        );

        return new OkResult<>(usuarioViewModel);
    }

    @Transactional
    public Result<UUID> add(CreateUsuarioDTO request) {
        var errors = validator.validate(request);
        if (!errors.isEmpty()) {
            return new UnprocessableEntityResult<>(errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
        }

        var cursosIds = request.getIdentificacoes()
            .stream()
            .map(CreateUsuarioIdentificacaoDTO::getCursoId)
            .distinct()
            .toList();
        var cursos = cursoRepository.findAllById(cursosIds);
        if(cursosIds.size() != cursos.size()) {
            return new NotFoundResult<>("Curso não encontrado");
        }

        var usuario = new Usuario(
            request.getNome(),
            request.getEmail(),
            request.getCpf(),
            request.getTelefone()
        );

        usuarioRepository.save(usuario);

        request.getIdentificacoes()
            .forEach(identificacaoRequest -> {
                var curso = cursos
                    .stream()
                    .filter(c -> c.getId().equals(identificacaoRequest.getCursoId()))
                    .findFirst()
                    .get();

                var identificacao = new Identificacao(
                    usuario,
                    identificacaoRequest.getNumero(),
                    identificacaoRequest.getTipo(),
                    identificacaoRequest.getSituacao(),
                    curso
                );
                usuario.addIdentificacao(identificacao);
                identificacaoRepository.save(identificacao);
            });

        return new CreatedResult<>(usuario.getId());
    }

//    public Result update(UUID id, UpdateCursoDTO request) {
//        var errors = validator.validate(request);
//        if (!errors.isEmpty()) {
//            return new UnprocessableEntityResult<>(errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
//        }
//
//        var curso = usuarioRepository.findById(id);
//        if (curso.isEmpty()) {
//            return new NotFoundResult<>("Curso não encontrado");
//        }
//
//        var cursoUpdate = curso.get();
//        cursoUpdate.setNome(request.getNome());
//        cursoUpdate.setCursoTipo(request.getCursoTipo());
//
//        usuarioRepository.save(cursoUpdate);
//
//        return new NoContentResult();
//    }
//
//    public Result remove(UUID id) {
//        var curso = usuarioRepository.findById(id);
//        if (curso.isEmpty()) {
//            return new NotFoundResult<>("Curso não encontrado");
//        }
//
//        var cursoRemove = curso.get();
//        usuarioRepository.delete(cursoRemove);
//
//        return new NoContentResult();
//    }
}
