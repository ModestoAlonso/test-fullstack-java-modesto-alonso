package com.modesto.prueba_tec.Filters;

import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;

import java.sql.Timestamp;
import java.util.List;

public class UsuarioFilter {
    private String nombre;
    private String apellido;
    private String email;
    private List<RolesDisponibles> roles;
    private List<EstadosDisponibles> estados;

    public UsuarioFilter(String nombre, String apellido, String email, List<RolesDisponibles> roles, List<EstadosDisponibles> estados) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.roles = roles;
        this.estados = estados;
    }

    public UsuarioFilter() {
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

    public List<RolesDisponibles> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesDisponibles> roles) {
        this.roles = roles;
    }

    public List<EstadosDisponibles> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadosDisponibles> estados) {
        this.estados = estados;
    }

    @Override
    public String toString() {
        return "UsuarioFilter{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", estados=" + estados +
                '}';
    }
}
