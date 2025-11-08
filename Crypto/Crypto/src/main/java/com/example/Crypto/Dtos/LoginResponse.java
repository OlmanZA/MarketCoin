package com.example.Crypto.Dtos;


import com.example.Crypto.Entities.Usuario;

public class LoginResponse {
    private boolean success;
    private String message;
    private Usuario usuario;

    public LoginResponse(boolean success, String message, Usuario usuario) {
        this.success = success;
        this.message = message;
        this.usuario = usuario;
    }

    // Getters y setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
