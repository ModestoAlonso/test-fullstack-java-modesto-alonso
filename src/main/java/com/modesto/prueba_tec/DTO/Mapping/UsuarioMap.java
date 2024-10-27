package com.modesto.prueba_tec.DTO.Mapping;

import com.modesto.prueba_tec.DTO.Auth.LoginDTO;
import com.modesto.prueba_tec.DTO.Auth.UsuarioLoggedDTO;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioDTO;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioModificar;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioRegistrar;
import com.modesto.prueba_tec.Entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface  UsuarioMap {
    /**
     * Transforma un UsuarioDTO a un Usuario
     * @param user Objeto User
     * @return UsuarioDTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "estado", target = "estado")
    UsuarioDTO toUsuarioDto(Usuario user);
    /**
     * Transforma un Usuario a un UsuarioRegistrar
     * @param userDto Objeto UsuarioRegistrar
     * @return Usuario
     */
    Usuario fromUsuarioDto(UsuarioRegistrar userDto);
    /**
     * Tranforma una lista de UsuarioDTO a una lista de Usuario
     * @param users Lista de Usuario List<Usuario>
     * @return List<UsuarioDTO>
     */
    List<UsuarioDTO> toUsuarioDto(List<Usuario> users);
    /**
     * Tranforma una lista de UsuarioDTO a una lista de Usuario
     * @param userDtos  Lista de UsuarioDTO List<UsuarioDTO>
     * @return List<Usuario>
     */
    List<Usuario> fromListUsuarioDto(List<UsuarioDTO> userDtos);

    /**
     * Transforma un signUpDto a un Usuario
     * @param signUpDto Objeto loginDTO
     * @return Usuario
     */
    Usuario logIn(LoginDTO signUpDto);

    /**
     * Transforma un UsuarioRegistrar a un Usuario
     * @param usuarioRegistrar Objeto UsuarioRegistrar
     * @return Usuario
     */
    Usuario registrar(UsuarioRegistrar usuarioRegistrar);

    /**
     * Transforma un usuario a un UsuarioLoggedDTO
     * @param usuario Objeto usuario
     * @return UsuarioLoggedDTO
     */
    UsuarioLoggedDTO toUsuarioLoggedDto(Usuario usuario);

}
