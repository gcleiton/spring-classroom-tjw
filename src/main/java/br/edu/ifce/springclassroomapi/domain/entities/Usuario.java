package br.edu.ifce.springclassroomapi.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario extends EntityBase {
    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @OneToMany(mappedBy = "usuario")
    private List<Identificacao> identificacoes;

    protected Usuario() { }

    public Usuario(String nome, String email, String cpf, String telefone) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.identificacoes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void addIdentificacao(Identificacao identificacao) {
        identificacoes.add(identificacao);
    }

    public void removeIdentificacao(Identificacao identificacao) {
        identificacoes.remove(identificacao);
    }

    public void updateIdentificacao(Identificacao identificacao) {
        identificacoes.set(identificacoes.indexOf(identificacao), identificacao);
    }

    public List<Identificacao> getIdentificacoes() {
        return identificacoes;
    }
}
