package br.edu.ifce.springclassroomapi.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum CursoTipo {
    Graduacao("Graduação"),
    Integrado("Integrado"),
    Tecnico("Técnico");

    public final String label;

    CursoTipo(String label) {
        this.label = label;
    }

    public static List<EnumValue> asList() {
        return Arrays.stream(CursoTipo.values()).map(cursoTipo -> new EnumValue(cursoTipo.name(), cursoTipo.getLabel())).toList();
    }

    public String getLabel() {
        return label;
    }
}
