package br.edu.ifce.springclassroomapi.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum IdentificacaoTipo {
    Aluno("Aluno"),
    Professor("Professor");

    public final String label;

    IdentificacaoTipo(String label) {
        this.label = label;
    }

    public static List<EnumValue> asList() {
        return Arrays.stream(IdentificacaoTipo.values()).map(identificacaoTipo -> new EnumValue(identificacaoTipo.name(), identificacaoTipo.getLabel())).toList();
    }

    public String getLabel() {
        return label;
    }
}
