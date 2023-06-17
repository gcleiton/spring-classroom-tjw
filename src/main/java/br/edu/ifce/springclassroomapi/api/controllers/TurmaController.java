package br.edu.ifce.springclassroomapi.api.controllers;

import br.edu.ifce.springclassroomapi.application.dtos.turma.TurmaDTO;
import br.edu.ifce.springclassroomapi.application.services.CursoService;
import br.edu.ifce.springclassroomapi.application.services.TurmaService;
import br.edu.ifce.springclassroomapi.application.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@org.springframework.stereotype.Controller
@RequestMapping("turmas")
public class TurmaController extends Controller {
    private final TurmaService turmaService;
    private final UsuarioService usuarioService;
    private final CursoService cursoService;

    @Autowired
    public TurmaController(TurmaService turmaService, UsuarioService usuarioService, CursoService cursoService) {
        this.turmaService = turmaService;
        this.usuarioService = usuarioService;
        this.cursoService = cursoService;
    }

    @GetMapping()
    public String index(Model model) {
        var result = turmaService.list();
        model.addAttribute("turmas", result.getData());
        return "/pages/turmas";
    }

    @GetMapping("cadastrar")
    public String renderCadastroPage(Model model) {
        addDefaultAttributesToCreate(model);
        return "/pages/create-turma";
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody TurmaDTO turmaDto) {
        var result = turmaService.add(turmaDto);
        return HandleResponse(result);
    }

    @GetMapping("{id}")
    public String renderEdicaoPage(@PathVariable UUID id, Model model) {
        var result = turmaService.loadById(id);
        if(result.isFailure()) {
            model.addAttribute("errors", result.getErrors());
            return "/pages/edit-turma";
        }
        addDefaultAttributesToCreate(model);
        var data = result.getData();
        var alunosByCurso = usuarioService.listAlunosByCursoId(data.getCurso().getId());
        model.addAttribute("turma", result.getData());
        model.addAttribute("alunos", alunosByCurso.getData());
        return "/pages/edit-turma";
    }

    @GetMapping("{id}/obter")
    public ResponseEntity loadById(@PathVariable UUID id) {
        var result = turmaService.loadById(id);
        return HandleResponse(result);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody TurmaDTO turmaDto) {
        var result = turmaService.update(id, turmaDto);
        return HandleResponse(result);
    }

    private void addDefaultAttributesToCreate(Model model) {
        var cursos = cursoService.list();
        var professores = usuarioService.listProfessores();
        model.addAttribute("cursos", cursos.getData());
        model.addAttribute("professores", professores.getData());
    }

    @DeleteMapping("{id}")
    public ResponseEntity remove(@PathVariable UUID id) {
        var result = turmaService.remove(id);
        return HandleResponse(result);
    }
}
