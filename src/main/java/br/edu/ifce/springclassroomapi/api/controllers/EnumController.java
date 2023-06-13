package br.edu.ifce.springclassroomapi.api.controllers;

import br.edu.ifce.springclassroomapi.core.results.OkResult;
import br.edu.ifce.springclassroomapi.domain.enums.CursoTipo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumController extends Controller {
    @GetMapping("/curso-tipos")
    public ResponseEntity list() {
        var result = new OkResult<>(CursoTipo.asList());
        return HandleResponse(result);
    }
}
