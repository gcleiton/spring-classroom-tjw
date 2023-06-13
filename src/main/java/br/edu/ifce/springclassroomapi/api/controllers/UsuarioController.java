package br.edu.ifce.springclassroomapi.api.controllers;

import br.edu.ifce.springclassroomapi.application.dtos.usuario.CreateUsuarioDTO;
import br.edu.ifce.springclassroomapi.application.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/usuarios")
@RestController
public class UsuarioController extends Controller {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

//    @GetMapping()
//    public ResponseEntity list() {
//        var result = usuarioService.list();
//        return HandleResponse(result);
//    }
//
    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable UUID id) {
        var result = usuarioService.loadById(id);
        return HandleResponse(result);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody CreateUsuarioDTO usuarioDTO) {
        var result = usuarioService.add(usuarioDTO);
        return HandleResponse(result);
    }
//
//    @PutMapping("{id}")
//    public ResponseEntity update(@PathVariable UUID id, @RequestBody UpdateCursoDTO cursoDTO) {
//        var result = usuarioService.update(id, cursoDTO);
//        return HandleResponse(result);
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity delete(@PathVariable UUID id) {
//        var result = usuarioService.remove(id);
//        return HandleResponse(result);
//    }
}
