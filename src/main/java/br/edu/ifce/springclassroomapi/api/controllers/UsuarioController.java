package br.edu.ifce.springclassroomapi.api.controllers;

import br.edu.ifce.springclassroomapi.application.dtos.usuario.ListUsuarioDTO;
import br.edu.ifce.springclassroomapi.application.dtos.usuario.UsuarioDTO;
import br.edu.ifce.springclassroomapi.application.services.CursoService;
import br.edu.ifce.springclassroomapi.application.services.UsuarioService;
import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@org.springframework.stereotype.Controller
@RequestMapping("usuarios")
public class UsuarioController extends Controller {
    private final UsuarioService usuarioService;
    private final CursoService cursoService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, CursoService cursoService) {
        this.usuarioService = usuarioService;
        this.cursoService = cursoService;
    }

    @GetMapping()
    public String index(Model model) {
        var result = usuarioService.list();
        model.addAttribute("usuarios", result.getData());
        return "/pages/usuarios";
    }

    @GetMapping("obter")
    public ResponseEntity listUsuarios(ListUsuarioDTO query)
    {
        var result = usuarioService.list(query.toSpecification());
        return HandleResponse(result);
    }

    @GetMapping("cadastrar")
    public String renderCadastroPage(Model model) {
        addDefaultAttributesToCreate(model, new UsuarioDTO());
        return "/pages/create-usuario";
    }

    @PostMapping("cadastrar")
    public String create(@ModelAttribute("usuarioDto") UsuarioDTO usuarioDto, Model model) {
        var result = usuarioService.add(usuarioDto);
        if (result.isFailure()) {
            model.addAttribute("errors", result.getErrors());
            addDefaultAttributesToCreate(model, usuarioDto);
            return "/pages/create-usuario";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("{id}")
    public String renderEdicaoPage(@PathVariable UUID id, Model model) {
        var result = usuarioService.loadById(id);
        if(result.isFailure()) {
            model.addAttribute("errors", result.getErrors());
            return "/pages/edit-usuario";
        }
        addDefaultAttributesToCreate(model, new UsuarioDTO());
        model.addAttribute("usuarioDto", result.getData());
        return "/pages/edit-usuario";
    }

    @GetMapping("{id}/obter")
    public ResponseEntity loadById(@PathVariable UUID id) {
        var result = usuarioService.loadById(id);
        return HandleResponse(result);
    }

    @PostMapping("{id}")
    public String update(@PathVariable UUID id, @ModelAttribute("usuarioDto") UsuarioDTO usuarioDto, Model model) {
        var result = usuarioService.update(id, usuarioDto);
        if (result.isFailure()) {
            model.addAttribute("errors", result.getErrors());
            addDefaultAttributesToCreate(model, usuarioDto);
            return "/pages/edit-usuario";
        }
        return "redirect:/usuarios";
    }

    private void addDefaultAttributesToCreate(Model model, UsuarioDTO usuarioDto) {
        var identificacaoTipos = IdentificacaoTipo.asList();
        var cursos = cursoService.list();
        model.addAttribute("identificacaoTipos", identificacaoTipos);
        model.addAttribute("cursos", cursos.getData());
        model.addAttribute("usuarioDto", usuarioDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deactivate(@PathVariable UUID id) {
        var result = usuarioService.remove(id);
        return HandleResponse(result);
    }
}
