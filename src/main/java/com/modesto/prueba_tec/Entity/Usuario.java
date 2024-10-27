package com.modesto.prueba_tec.Entity;

import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usuario_id", nullable=false)
    private int id;

    @Column(name = "usuario_nombre", nullable=false)
    private String nombre;

    @Column(name = "usuario_apellido", nullable=false)
    private String apellido;

    @Column(name = "usuario_password", nullable=false)
    private String password;

    @Column(name = "usuario_email",nullable=false, unique=true)
    @Email(message = "Ingrese un email válido",
            regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
    private String email;

    @Column(name = "rol", nullable=false)
    private RolesDisponibles rol;

    @Column(name = "estado", nullable = false)
    private EstadosDisponibles estado;

    @Column(name="created_at", nullable=false)
    @CreationTimestamp
    private Timestamp creado;

    @Column(name="updated_at", nullable=false)
    @UpdateTimestamp
    private Timestamp modificado;

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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public EstadosDisponibles getEstado() {
        return estado;
    }
    public void setEstado(EstadosDisponibles estado) {
        this.estado = estado;
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

    public Usuario(int id, String nombre, String apellido, String password, String email, RolesDisponibles rol, EstadosDisponibles estado, Timestamp creado, Timestamp modificado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
        this.creado = creado;
        this.modificado = modificado;
    }

    public Usuario() {
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", rol=" + rol +
                ", estado=" + estado +
                ", creado=" + creado +
                ", modificado=" + modificado +
                '}';
    }
}
