package com.modesto.prueba_tec.DTO.Usuarios;

import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class UsuarioModificar {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private RolesDisponibles rol;
    private EstadosDisponibles estado;

    public UsuarioModificar(String nombre, String apellido, String email, String password, RolesDisponibles rol, EstadosDisponibles estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
    }

    public UsuarioModificar() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolesDisponibles getRol() {
        return rol;
    }

    public void setRol(RolesDisponibles rol) {
        this.rol = rol;
    }

    public EstadosDisponibles getEstado() {
        return estado;
    }

    public void setEstado(EstadosDisponibles estado) {
        this.estado = estado;
    }

    public boolean todosNulos() {
        return Stream.of(nombre, apellido, password, email, rol, estado)
                .allMatch(Objects::isNull);
    }
}



