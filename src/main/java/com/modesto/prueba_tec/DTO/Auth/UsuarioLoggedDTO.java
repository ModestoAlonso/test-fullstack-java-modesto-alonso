package com.modesto.prueba_tec.DTO.Auth;

import com.modesto.constants.auth.RolesDisponibles;

import java.sql.Timestamp;

public class UsuarioLoggedDTO {
    private String nombre;
    private String apellido;
    private String email;
    private RolesDisponibles rol;
    private String token;

    public UsuarioLoggedDTO(String nombre, String apellido, String email, RolesDisponibles rol, String token) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
        this.token = token;
    }

    public UsuarioLoggedDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RolesDisponibles getRol() {
        return rol;
    }

    public void setRol(RolesDisponibles rol) {
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
