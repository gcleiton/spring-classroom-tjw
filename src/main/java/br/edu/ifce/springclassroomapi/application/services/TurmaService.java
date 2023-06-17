package br.edu.ifce.springclassroomapi.application.services;

import br.edu.ifce.springclassroomapi.application.dtos.turma.TurmaDTO;
import br.edu.ifce.springclassroomapi.application.viewmodels.ViewModelWithIdAndNome;
import br.edu.ifce.springclassroomapi.application.viewmodels.turma.GetTurmaViewModel;
import br.edu.ifce.springclassroomapi.application.viewmodels.turma.ListTurmaViewModel;
import br.edu.ifce.springclassroomapi.application.viewmodels.usuario.ListUsuarioViewModel;
import br.edu.ifce.springclassroomapi.core.results.*;
import br.edu.ifce.springclassroomapi.domain.entities.Turma;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TurmaService {
    private final TurmaRepository turmaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final Validator validator;
    private final Sort defaultSort = Sort.by(Sort.Direction.DESC, "ano", "semestre");

    public TurmaService(
        TurmaRepository turmaRepository,
        UsuarioRepository usuarioRepository,
        CursoRepository cursoRepository,
        Validator validator)
    {
        this.turmaRepository = turmaRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.validator = validator;
    }

    public Result<List<ListTurmaViewModel>> list() {
        var turmas = turmaRepository.findAll(defaultSort);
        var turmasViewModel = turmas.stream().map(turma -> new ListTurmaViewModel(
            turma.getId(),
            turma.getNome(),
            turma.getAno(),
            turma.getSemestre(),
            new ViewModelWithIdAndNome(turma.getCurso().getId(), turma.getCurso().getNome()),
            new ViewModelWithIdAndNome(turma.getProfessorResponsavel().getId(), turma.getProfessorResponsavel().getNome()),
            turma.getAlunos().size()
        )).toList();

        return new OkResult<>(turmasViewModel);
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
        var alunos = usuarioRepository.findAlunosByCursoId(cursoId);
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

    public Result<GetTurmaViewModel> loadById(UUID id) {
        var turma = turmaRepository.findById(id);
        if (turma.isEmpty()) {
            return new NotFoundResult<>("Turma não encontrada");
        }

        var turmaEntity = turma.get();
        var turmaViewModel = new GetTurmaViewModel(
            turmaEntity.getId(),
            turmaEntity.getNome(),
            turmaEntity.getAno(),
            turmaEntity.getSemestre(),
            new ViewModelWithIdAndNome(turmaEntity.getCurso().getId(), turmaEntity.getCurso().getNome()),
            new ViewModelWithIdAndNome(turmaEntity.getProfessorResponsavel().getId(), turmaEntity.getProfessorResponsavel().getNome()),
            turmaEntity.getAlunos().stream().map(aluno -> new ListUsuarioViewModel(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getIdentificacao(),
                aluno.getIdentificacaoTipo(),
                aluno.getCurso() != null ? new ViewModelWithIdAndNome(aluno.getCurso().getId(), aluno.getCurso().getNome()) : null
            )).toList()
        );

        return new OkResult<>(turmaViewModel);
    }

    public Result<UUID> add(TurmaDTO request) {
        var errors = validator.validate(request);
        if (!errors.isEmpty()) {
            return new UnprocessableEntityResult<>(errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
        }

        var curso = cursoRepository.findById(request.getCursoId());
        if (curso.isEmpty()) {
            return new NotFoundResult<>("Curso não encontrado");
        }

        var professorResponsavel = usuarioRepository.findByIdAndIdentificacaoTipo(request.getProfessorResponsavelId(), IdentificacaoTipo.Professor);
        if (professorResponsavel.isEmpty()) {
            return new NotFoundResult<>("Professor responsável não encontrado");
        }

        var alunos = usuarioRepository.findByIdsAndIdentificacaoTipo(request.getAlunosIds().stream().toList(), IdentificacaoTipo.Aluno);
        if (alunos.size() != request.getAlunosIds().size()) {
            return new NotFoundResult<>("Aluno não encontrado");
        }

        var turma = new Turma(
            request.getNome(),
            request.getAno(),
            request.getSemestre(),
            curso.get(),
            professorResponsavel.get(),
            alunos
        );

        turmaRepository.save(turma);

        return new CreatedResult<>(turma.getId());
    }

    public Result update(UUID id, TurmaDTO request) {
        var errors = validator.validate(request);
        if (!errors.isEmpty()) {
            return new UnprocessableEntityResult<>(errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
        }

        var turma = turmaRepository.findById(id);
        if (turma.isEmpty()) {
            return new NotFoundResult<>("Turma não encontrada");
        }
        var turmaEntity = turma.get();

        var curso = cursoRepository.findById(request.getCursoId());
        if (curso.isEmpty()) {
            return new NotFoundResult<>("Curso não encontrado");
        }

        var professorResponsavel = usuarioRepository.findByIdAndIdentificacaoTipo(request.getProfessorResponsavelId(), IdentificacaoTipo.Professor);
        if (professorResponsavel.isEmpty()) {
            return new NotFoundResult<>("Professor responsável não encontrado");
        }

        var alunos = usuarioRepository.findByIdsAndIdentificacaoTipo(request.getAlunosIds().stream().toList(), IdentificacaoTipo.Aluno);
        if (alunos.size() != request.getAlunosIds().size()) {
            return new NotFoundResult<>("Aluno não encontrado");
        }

        turmaEntity.setNome(request.getNome());
        turmaEntity.setAno(request.getAno());
        turmaEntity.setSemestre(request.getSemestre());
        turmaEntity.setCurso(curso.get());
        turmaEntity.setProfessorResponsavel(professorResponsavel.get());
        turmaEntity.setAlunos(alunos);

        turmaRepository.save(turmaEntity);

        return new NoContentResult();
    }

    public Result remove(UUID id) {
        var turma = turmaRepository.findById(id);
        if (turma.isEmpty()) {
            return new NotFoundResult<>("Usuário não encontrado");
        }

        turmaRepository.delete(turma.get());

        return new NoContentResult();
    }

}
