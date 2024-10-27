package com.modesto.prueba_tec.Services;

import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;
import com.modesto.prueba_tec.DTO.Mapping.UsuarioMap;
import com.modesto.prueba_tec.DTO.Response.RestResponse;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioModificar;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioRegistrar;
import com.modesto.prueba_tec.Entity.Usuario;
import com.modesto.prueba_tec.Filters.UsuarioFilter;
import com.modesto.prueba_tec.Repositories.UsuarioRepository;
import com.modesto.prueba_tec.Specifications.UsuarioSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMap usuarioMap;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMap usuarioMap, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMap = usuarioMap;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Obtiene una lista de usuarios segun los filtros ingresados
     * @param filtrosCampos UsuarioFilter contienendo campos relacionados a la consulta.
     * @param pagina    Pàgina actual
     * @param paginaSize    El Size de la Pàgina
     * @return
     */
    public ResponseEntity<Object> find(UsuarioFilter filtrosCampos, Integer pagina, Integer paginaSize) {
        Specification<Usuario> usuarioSpecification = UsuarioSpecification.filtrar(filtrosCampos);

        List<Usuario> usuarios = usuarioRepository.findAll(usuarioSpecification, PageRequest.of(pagina, paginaSize))
                .stream()
                .toList();
        String mensaje = usuarios.isEmpty() ? "Sin datos que mostrar" : "Datos Obtenidos correctamente";

        return RestResponse.generateResponse(mensaje,
                HttpStatus.OK,usuarioMap.toUsuarioDto(usuarios));
    }

    /**
     * Crea un nuevo usuario en caso de que este no exista.
     * @param nuevoUsuarioDTO Datos del nuevo usuario a registrar
     * @return UsuarioDTO Usuario creado
     */
    public ResponseEntity<Object> registrar(UsuarioRegistrar nuevoUsuarioDTO) {
        Optional<Usuario> existUsuario =  usuarioRepository.findByEmail(nuevoUsuarioDTO.getEmail());
        if(existUsuario.isPresent()){
            return RestResponse.generateResponse("El email ya esta siendo utilizado",
                    HttpStatus.BAD_REQUEST,null);
        }

        Usuario usuario = usuarioMap.registrar(nuevoUsuarioDTO);
        usuario.setRol(RolesDisponibles.CONSULTOR);
        usuario.setEstado(EstadosDisponibles.ACTIVO);
        usuario.setPassword(passwordEncoder.encode(nuevoUsuarioDTO.getPassword()));
        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        return RestResponse.generateResponse("Usuario creado correctamente",
                HttpStatus.OK,usuarioMap.toUsuarioDto(nuevoUsuario));
    }

    /**
     * Elimina un usuario en caso de que este exista.
     * @param idUsuario Id del usuario a eliminar
     * @return
     */
    public ResponseEntity<Object> eliminar(Integer idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if(usuario.isPresent()){
            usuarioRepository.deleteById(idUsuario);
            return RestResponse.generateResponse("Usuario eliminado correctamente",
                    HttpStatus.OK,null);
        }
        return RestResponse.generateResponse("Usuario no encontrado",
                HttpStatus.BAD_REQUEST,null);
    }


    public ResponseEntity<Object> editar(int idUsuario, UsuarioModificar usuarioModificar) {
        if(usuarioModificar.todosNulos()){
            return RestResponse.generateResponse("Ingrese un campo válido.",
                    HttpStatus.BAD_REQUEST,null);
        }

        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if(usuario.isPresent()){
            if(usuarioModificar.getEmail()!= null && !usuarioModificar.getEmail().isEmpty()){
                Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(usuarioModificar.getEmail());
                if(usuarioEmail.isPresent()){
                    Usuario usuariodbmail = usuarioEmail.get();
                    if(usuariodbmail.getId() != idUsuario){
                        return RestResponse.generateResponse("Email en uso, utilice otro.",
                                HttpStatus.BAD_REQUEST,null);
                    }
                }
            }

            if(usuario.get().getRol().equals(RolesDisponibles.ADMIN) &&
                    ((usuarioModificar.getRol()!= null && !usuarioModificar.getRol().equals(RolesDisponibles.ADMIN))
                            || (usuarioModificar.getEstado()!= null && usuarioModificar.getEstado().equals(EstadosDisponibles.INACTIVO)))){

                List<Usuario> usuarios = usuarioRepository.findByRolAndEstado(RolesDisponibles.ADMIN, EstadosDisponibles.ACTIVO);
                if(usuarios.size() == 1){
                    return RestResponse.generateResponse("Debe existir al menos 1 ADMIN activo.",
                            HttpStatus.BAD_REQUEST,null);
                }
            }

            Usuario usuarioDB = usuario.get();
            usuarioDB.setEmail(usuarioModificar.getEmail() !=null && !usuarioModificar.getEmail().isEmpty()
                    ? usuarioModificar.getEmail()
                    : usuarioDB.getEmail());
            usuarioDB.setNombre(usuarioModificar.getNombre() !=null && !usuarioModificar.getNombre().isEmpty()
                    ? usuarioModificar.getNombre()
                    : usuarioDB.getNombre());
            usuarioDB.setApellido(usuarioModificar.getApellido() !=null && !usuarioModificar.getApellido().isEmpty()
                    ? usuarioModificar.getApellido()
                    : usuarioDB.getApellido());
            usuarioDB.setRol(usuarioModificar.getRol() != null
                    ? usuarioModificar.getRol()
                    : usuarioDB.getRol());
            usuarioDB.setEstado(usuarioModificar.getEstado() != null
                    ? usuarioModificar.getEstado()
                    : usuarioDB.getEstado());


            if(usuarioModificar.getPassword() != null && !usuarioModificar.getPassword().isEmpty()){
                usuarioDB.setPassword(usuarioModificar.getPassword().isEmpty() ? usuarioDB.getPassword() : passwordEncoder.encode(usuarioModificar.getPassword()));
            }

            Usuario usuarioModificado = usuarioRepository.save(usuarioDB);
            return RestResponse.generateResponse("Usuario editado correctamente",
                    HttpStatus.OK,usuarioMap.toUsuarioDto(usuarioModificado));
        }
        return RestResponse.generateResponse("Usuario no encontrado",
                HttpStatus.BAD_REQUEST,null);
    }
}
