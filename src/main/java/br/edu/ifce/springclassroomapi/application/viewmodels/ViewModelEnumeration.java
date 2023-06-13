package br.edu.ifce.springclassroomapi.application.viewmodels;

public class ViewModelEnumeration {
    private final String value;
    private final String label;

    public ViewModelEnumeration(String value, String label) {
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
