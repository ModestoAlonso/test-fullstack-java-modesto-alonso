package com.modesto.prueba_tec.Unitarios;
import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;
import org.assertj.core.api.Assertions;
import com.modesto.prueba_tec.Entity.Usuario;
import com.modesto.prueba_tec.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.*;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Test 1:Crear Usuario")
    @Order(1)
    @Rollback(value = false)
    public void crearUsuarioTest(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setApellido("User 1");
        usuario.setEmail("test"+Math.random()+"@test.com");
        usuario.setPassword("Testing123");
        usuario.setRol(RolesDisponibles.ADMIN);
        usuario.setEstado(EstadosDisponibles.ACTIVO);

        Usuario newUsuario = usuarioRepository.save(usuario);
        System.out.println(newUsuario);
        Assertions.assertThat(newUsuario.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test 2:Obtener Usuario")
    @Order(2)
    public void obtenerUsuarioTest(){
        Usuario usuario = usuarioRepository.findById(1).get();
        System.out.println(usuario);
        Assertions.assertThat(usuario.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test 3:Modificar Usuario")
    @Order(3)
    @Rollback(value = false)
    public void actualizarUsuarioTest(){
        Usuario usuario = usuarioRepository.findById(1).get();
        usuario.setNombre("Test Superado");
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        System.out.println(usuario);
        Assertions.assertThat(usuarioActualizado.getNombre()).isEqualTo("Test Superado");
    }

    @Test
    @DisplayName("Test 3:Eliminar-Desactivar Usuario")
    @Order(3)
    @Rollback(value = false)
    public void eliminarDesactivarUsuarioTest(){
        Usuario usuario = usuarioRepository.findById(1).get();
        usuario.setEstado(EstadosDisponibles.INACTIVO);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        System.out.println(usuario);
        Assertions.assertThat(usuarioActualizado.getEstado()).isEqualTo(EstadosDisponibles.INACTIVO);
    }

}
