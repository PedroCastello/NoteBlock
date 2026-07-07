package com.noteblock.noteblock.dto;

public class AuthTokenResponseDTO {

    private String token;
    private String tipo;

    public AuthTokenResponseDTO(String token) {
        this.token = token;
        this.tipo = "Bearer";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
