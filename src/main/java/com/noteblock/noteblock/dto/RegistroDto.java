package com.noteblock.noteblock.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistroDto {

    @NotBlank(message = "O nome e obrigatorio.")
    @Size(max = 100, message = "O nome nao pode exceder 100 caracteres.")
    private String nome;

    @NotBlank(message = "O email e obrigatorio.")
    @Email(message = "Informe um email valido.")
    private String email;

    @NotBlank(message = "A senha e obrigatoria.")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
    private String senha;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
