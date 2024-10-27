package com.modesto.prueba_tec.Unitarios;


import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;
import com.modesto.prueba_tec.Controllers.UsuariosController;
import com.modesto.prueba_tec.DTO.Response.RestResponse;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioDTO;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioModificar;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioRegistrar;
import com.modesto.prueba_tec.Filters.UsuarioFilter;
import com.modesto.prueba_tec.Services.UsuarioService;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuariosController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class UsuarioControllerTest {
    @Autowired
    public MockMvc mvc;

    @Autowired
    public ObjectMapper objectMapper;

    @MockBean
    public UsuarioService service;

    UsuarioDTO usuario;
    List<UsuarioDTO> usuariosDTO;
    @Autowired
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() throws Exception {
        usuario = new UsuarioDTO();
        usuario.setId(1);
        usuario.setNombre("UserTest");
        usuario.setApellido("Test");
        usuario.setEmail("test@test.com");
        usuario.setEstado(EstadosDisponibles.ACTIVO);
        usuario.setRol(RolesDisponibles.ADMIN);

        UsuarioDTO usuarioDos = new UsuarioDTO();

        usuarioDos.setId(2);
        usuarioDos.setNombre("UserTest 2");
        usuarioDos.setApellido("Test 2");
        usuarioDos.setEmail("test2@test.com");
        usuarioDos.setEstado(EstadosDisponibles.ACTIVO);
        usuarioDos.setRol(RolesDisponibles.CONSULTOR);

        usuariosDTO = new ArrayList<>();
        usuariosDTO.add(usuarioDos);
    }

    @Test
    @DisplayName("Test 1:Crear Usuario")
    @Order(1)
    public void createUsuario() throws Exception {
        ResponseEntity<Object> responseEntity = RestResponse.generateResponse("Usuarios obtenidos correctamente", HttpStatus.OK, usuario);

        given(usuarioService.registrar(any(UsuarioRegistrar.class))).willReturn(responseEntity);

        ResultActions response = mvc.perform(post("/usuario/crear")
                        .with(SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseEntity)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(usuario.getId())))
                .andExpect(jsonPath("$.data.email", is(usuario.getEmail())))
                .andExpect(jsonPath("$.data.nombre", is(usuario.getNombre())));


    }

    @Test
    @DisplayName("Test 2: Obtener Usuario")
    @Order(2)
    public void obtenerUsuario() throws Exception {

        ResponseEntity<Object> responseEntity = RestResponse.generateResponse("Usuarios obtenidos correctamente", HttpStatus.OK, usuariosDTO);

        given(usuarioService.find(any(UsuarioFilter.class),any(Integer.class),any(Integer.class))).willReturn(responseEntity);

        ResultActions response = mvc.perform(get("/usuario/listar?email=test2@test.com")
                .with(SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("ADMIN"))));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.size()", is(1)))
                .andExpect(jsonPath("$.data[0].email", is("test2@test.com")));
    }

    @Test
    @DisplayName("Test 3: Modificar Usuario")
    @Order(3)
    public void modificarUsuario() throws Exception {
        usuario.setNombre("Roberto");
        usuario.setRol(RolesDisponibles.CONSULTOR);
        ResponseEntity<Object> responseEntity = RestResponse.generateResponse("Usuario modificado correctamente", HttpStatus.OK, usuario);

        given(usuarioService.editar(any(Integer.class),any(UsuarioModificar.class))).willReturn(responseEntity);

        ResultActions response = mvc.perform(put("/usuario/editar/{id}", usuario.getId())
                .with(SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("ADMIN")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseEntity)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.nombre", is(usuario.getNombre())))
                .andExpect(jsonPath("$.data.rol", is("CONSULTOR")));

    }

    @Test
    @DisplayName("Test 4: Eliminar Usuario")
    @Order(4)
    public void eliminarUsuario() throws Exception {
        ResponseEntity<Object> responseEntity = RestResponse.generateResponse("Usuario eliminado correctamente", HttpStatus.OK, null);

        given(usuarioService.editar(any(Integer.class),any(UsuarioModificar.class))).willReturn(responseEntity);

        ResultActions response = mvc.perform(delete("/usuario/eliminar/{id}", usuario.getId())
                .with(SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("ADMIN")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseEntity)));

        response.andExpect(status().isOk())
                .andDo(print());

    }
}
