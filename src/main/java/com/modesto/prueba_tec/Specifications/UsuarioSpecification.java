package com.modesto.prueba_tec.Specifications;

import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioDTO;
import com.modesto.prueba_tec.Entity.Usuario;
import com.modesto.prueba_tec.Filters.UsuarioFilter;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

public class UsuarioSpecification {
    /**
     * Genera un Specification de Uusuario para relizar filtro por nombre, apellido, email, rol y estado
     * @param filtro
     * @return
     */
    public static Specification<Usuario> filtrar(UsuarioFilter filtro) {

        return Specification.where(nombreLike(filtro.getNombre()))
                .and(apellidoLike(filtro.getApellido()))
                .and(emailLike(filtro.getEmail()))
                .and(rolEn(filtro.getRoles()))
                .and(estadoEn(filtro.getEstados()));
    }

    private UsuarioSpecification() {

    }

    public static Specification<Usuario> nombreLike(String nombre) {
        return (root, query, builder) -> nombre != null  && !nombre .isEmpty()
                ? builder.like(builder.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%")
                : builder.conjunction();
    }
    public static Specification<Usuario> apellidoLike(String apellido) {
        return (root, query, builder) -> apellido != null  && !apellido .isEmpty()
                ? builder.like(builder.lower(root.get("apellido")), "%" + apellido.toLowerCase() + "%")
                : builder.conjunction();
    }
    public static Specification<Usuario> emailLike(String email) {
        return (root, query, builder) -> email != null  && !email.isEmpty()
                ? builder.like(root.get("email"), "%" + email + "%")
                : builder.conjunction();
    }
    public static Specification<Usuario> rolEn(List<RolesDisponibles> roles) {
        return (root, query, builder) -> roles !=null && !roles.isEmpty()
                ? root.get("rol").in(roles)
                : builder.conjunction();
    }
    public static Specification<Usuario> estadoEn(List<EstadosDisponibles> estados) {
        return (root, query, builder) -> estados !=null && !estados.isEmpty()
                ? root.get("estado").in(estados)
                : builder.conjunction();
    }

}
