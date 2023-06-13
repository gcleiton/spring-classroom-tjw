package br.edu.ifce.springclassroomapi.domain.enums;

public class EnumValue {
    private final String value;
    private final String label;

    public EnumValue(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }
    public String getLabel() {
        return label;
    }
}
