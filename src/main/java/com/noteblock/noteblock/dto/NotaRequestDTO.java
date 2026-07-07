package com.noteblock.noteblock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NotaRequestDTO {

    @NotBlank(message = "O título da nota não pode estar em branco.")
    @Size(max = 150, message = "O título da nota não pode exceder 150 caracteres.")
    private String titulo;

    @Size(max = 1000, message = "O conteúdo da nota não pode exceder 1000 caracteres.")
    private String conteudo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

}
