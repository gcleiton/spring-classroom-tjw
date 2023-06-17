package br.edu.ifce.springclassroomapi.api.controllers;

import br.edu.ifce.springclassroomapi.application.dtos.curso.CreateCursoDTO;
import br.edu.ifce.springclassroomapi.application.dtos.curso.UpdateCursoDTO;
import br.edu.ifce.springclassroomapi.application.services.CursoService;
import br.edu.ifce.springclassroomapi.domain.enums.CursoTipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@org.springframework.stereotype.Controller
@RequestMapping("cursos")
public class CursoController extends Controller {
    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping()
    public String index(Model model) {
        var result = cursoService.list();
        model.addAttribute("cursos", result.getData());
        model.addAttribute("cursoTipos", CursoTipo.asList());
        return "/pages/cursos";
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable UUID id) {
        var result = cursoService.loadById(id);
        return HandleResponse(result);
    }

    @PostMapping()
    public ResponseEntity create(CreateCursoDTO cursoDTO) {
        var result = cursoService.add(cursoDTO);
        return HandleResponse(result);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable UUID id, UpdateCursoDTO cursoDTO) {
        var result = cursoService.update(id, cursoDTO);
        return HandleResponse(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        var result = cursoService.remove(id);
        return HandleResponse(result);
    }
}
