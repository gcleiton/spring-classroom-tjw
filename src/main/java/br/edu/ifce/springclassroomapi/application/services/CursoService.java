package br.edu.ifce.springclassroomapi.application.services;

import br.edu.ifce.springclassroomapi.application.dtos.curso.CreateCursoDTO;
import br.edu.ifce.springclassroomapi.application.dtos.curso.UpdateCursoDTO;
import br.edu.ifce.springclassroomapi.application.viewmodels.curso.GetCursoViewModel;
import br.edu.ifce.springclassroomapi.application.viewmodels.curso.ListCursoViewModel;
import br.edu.ifce.springclassroomapi.core.results.*;
import br.edu.ifce.springclassroomapi.domain.entities.Curso;
import br.edu.ifce.springclassroomapi.infrastructure.repositories.CursoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    private final Validator validator;

    public CursoService(CursoRepository cursoRepository, Validator validator) {
        this.cursoRepository = cursoRepository;
        this.validator = validator;
    }

    public Result<List<ListCursoViewModel>> list() {
        var cursos = cursoRepository.findAll();
        var cursosViewModel = cursos.stream().map(curso -> new ListCursoViewModel(
                curso.getId(),
                curso.getNome(),
                curso.getCursoTipo()
        )).collect(Collectors.toList());

        return new OkResult<>(cursosViewModel);
    }

    public Result<GetCursoViewModel> loadById(UUID id) {
        var curso = cursoRepository.findById(id);
        if (curso.isEmpty()) {
            return new NotFoundResult<>("Curso não encontrado");
        }

        var cursoEntity = curso.get();
        var cursoViewModel = new GetCursoViewModel(
                cursoEntity.getId(),
                cursoEntity.getNome(),
                cursoEntity.getCursoTipo()
        );

        return new OkResult<>(cursoViewModel);
    }

    public Result<UUID> add(CreateCursoDTO request) {
        var errors = validator.validate(request);
        if (!errors.isEmpty()) {
            return new UnprocessableEntityResult<>(errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
        }

        var curso = new Curso(
            request.getNome(),
            request.getCursoTipo()
        );

        cursoRepository.save(curso);

        return new CreatedResult<>(curso.getId());
    }

    public Result update(UUID id, UpdateCursoDTO request) {
        var errors = validator.validate(request);
        if (!errors.isEmpty()) {
            return new UnprocessableEntityResult<>(errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
        }

        var curso = cursoRepository.findById(id);
        if (curso.isEmpty()) {
            return new NotFoundResult<>("Curso não encontrado");
        }

        var cursoUpdate = curso.get();
        cursoUpdate.setNome(request.getNome());
        cursoUpdate.setCursoTipo(request.getCursoTipo());

        cursoRepository.save(cursoUpdate);

        return new NoContentResult();
    }

    public Result remove(UUID id) {
        var curso = cursoRepository.findById(id);
        if (curso.isEmpty()) {
            return new NotFoundResult<>("Curso não encontrado");
        }

        var cursoRemove = curso.get();
        cursoRepository.delete(cursoRemove);

        return new NoContentResult();
    }
}
