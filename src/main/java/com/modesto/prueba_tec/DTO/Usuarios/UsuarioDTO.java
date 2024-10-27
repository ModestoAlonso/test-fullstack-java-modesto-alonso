package com.modesto.prueba_tec.DTO.Usuarios;

import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;

import java.sql.Timestamp;

public class UsuarioDTO {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private RolesDisponibles rol;
    private EstadosDisponibles estado;
    private Timestamp creado;
    private Timestamp modificado;

    public UsuarioDTO(int id, String nombre, String apellido, String email, RolesDisponibles rol, EstadosDisponibles estado, Timestamp creado, Timestamp modificado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
        this.creado = creado;
        this.modificado = modificado;
    }

    public UsuarioDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Timestamp getCreado() {
        return creado;
    }

    public void setCreado(Timestamp creado) {
        this.creado = creado;
    }

    public Timestamp getModificado() {
        return modificado;
    }

    public void setModificado(Timestamp modificado) {
        this.modificado = modificado;
    }

    public EstadosDisponibles getEstado() {
        return estado;
    }

    public void setEstado(EstadosDisponibles estado) {
        this.estado = estado;
    }
}
