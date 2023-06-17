package br.edu.ifce.springclassroomapi.application.dtos.usuario;

import br.edu.ifce.springclassroomapi.domain.enums.IdentificacaoTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UsuarioDTO {
    private UUID id;
    @NotBlank(message = "O nome do usuário é obrigatório")
    private String nome;
    @NotBlank(message = "O e-mail do usuário é obrigatório")
    private String email;
    @NotBlank(message = "O CPF do usuário é obrigatório")
    private String cpf;
    private String telefone;
    @NotBlank(message = "O número de identificação do usuário é obrigatório")
    private String identificacao;
    @NotNull(message = "O tipo de identificação do usuário é obrigatório")
    private IdentificacaoTipo identificacaoTipo;
    private UUID cursoId;

    public UsuarioDTO() {
    }

    public UsuarioDTO(
        UUID id,
        String nome,
        String email,
        String cpf,
        String telefone,
        String identificacao,
        IdentificacaoTipo identificacaoTipo,
        UUID cursoId)
    {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.identificacao = identificacao;
        this.identificacaoTipo = identificacaoTipo;
        this.cursoId = cursoId;
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

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public IdentificacaoTipo getIdentificacaoTipo() {
        return identificacaoTipo;
    }

    public void setIdentificacaoTipo(IdentificacaoTipo identificacaoTipo) {
        this.identificacaoTipo = identificacaoTipo;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
