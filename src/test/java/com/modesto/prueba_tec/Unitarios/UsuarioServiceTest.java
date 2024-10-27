package com.modesto.prueba_tec.Unitarios;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;
import com.modesto.prueba_tec.DTO.Response.RestResponse;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioDTO;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioModificar;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioRegistrar;
import com.modesto.prueba_tec.Entity.Usuario;
import com.modesto.prueba_tec.Filters.UsuarioFilter;
import com.modesto.prueba_tec.Repositories.UsuarioRepository;
import com.modesto.prueba_tec.Services.UsuarioService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioServiceTest {


    @Mock
    public UsuarioService usuarioService;

    public UsuarioDTO usuario;
    public List<UsuarioDTO> usuarios;
    public ObjectMapper mapper;
    @BeforeEach
    public void setUp() {
        usuario = new UsuarioDTO();
        usuario.setNombre("Usuario");
        usuario.setApellido("Test");
        usuario.setEmail("test"+Math.random()+"@test.com");
        usuario.setRol(RolesDisponibles.ADMIN);
        usuario.setEstado(EstadosDisponibles.ACTIVO);
        usuario.setId(1);

        usuarios = new ArrayList<UsuarioDTO>();
        usuarios.add(usuario);

        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Test 1: Crear Usuario")
    @Order(1)
    public void testCrearUsuario() throws Exception {

        ResponseEntity<Object> responseEntity = RestResponse.generateResponse("Usuarios obtenidos correctamente", HttpStatus.OK, usuario);
        UsuarioRegistrar usuarioARegistrar = new UsuarioRegistrar();
        usuarioARegistrar.setNombre("Usuario");
        usuarioARegistrar.setApellido("Test S");
        usuarioARegistrar.setEmail(usuario.getEmail());
        usuarioARegistrar.setPassword("test123");

        given(usuarioService.registrar(usuarioARegistrar)).willReturn(responseEntity);

        ResponseEntity<Object> responsetoSend = usuarioService.registrar(usuarioARegistrar);

        System.out.println(mapper.writeValueAsString(responsetoSend));
        assertThat(responsetoSend.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Test 2: Modificar Usuario")
    @Order(2)
    public void testModificarUsuario() throws Exception {
        usuario.setNombre("TestSuperado");
        ResponseEntity<Object> responseEntity = RestResponse.generateResponse("Usuario modificado correctamente", HttpStatus.OK, usuario);
        UsuarioModificar usuarioModificar = new UsuarioModificar();
        usuarioModificar.setId(1);
        usuarioModificar.setNombre("TestSuperado");
        given(usuarioService.editar(any(Integer.class),any(UsuarioModificar.class))).willReturn(responseEntity);

        ResponseEntity<Object> responsetoSend = usuarioService.editar(1,usuarioModificar);
        System.out.println(mapper.writeValueAsString(responsetoSend));
        assertThat(responsetoSend.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Test 3: Obtener Usuarios")
    @Order(3)
    public void testObtenerUusarios() throws Exception {
        ResponseEntity<Object> responseEntity = RestResponse.generateResponse("Usuarios Obtenidos", HttpStatus.OK, usuarios);
        given(usuarioService.find(any(UsuarioFilter.class),any(Integer.class),any(Integer.class))).willReturn(responseEntity);
        UsuarioFilter filtro = new UsuarioFilter();
        ResponseEntity<Object> responsetoSend = usuarioService.find(filtro, 0,10);
        System.out.println(mapper.writeValueAsString(responsetoSend));
        assertThat(responsetoSend.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Test 4: Eliminar Usuario")
    @Order(4)
    public void testEliminarUsuario() throws Exception {
        ResponseEntity<Object> responseEntity = RestResponse.generateResponse("Usuario eliminado correctamente", HttpStatus.OK, null);
        given(usuarioService.eliminar(any(Integer.class))).willReturn(responseEntity);
        ResponseEntity<Object> responsetoSend = usuarioService.eliminar(1);
        System.out.println(mapper.writeValueAsString(responsetoSend));
        assertThat(responsetoSend.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

    }
}
