package com.modesto.prueba_tec.Repositories;


import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;
import com.modesto.prueba_tec.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndEstado(String email, EstadosDisponibles estado);
    Usuario findByEstadoAndEmailIgnoreCase(EstadosDisponibles estado, String email);
    List<Usuario> findByRolAndEstado(RolesDisponibles rol, EstadosDisponibles estado);
}