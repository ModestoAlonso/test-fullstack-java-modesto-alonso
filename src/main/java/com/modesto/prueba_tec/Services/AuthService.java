package com.modesto.prueba_tec.Services;

import com.modesto.config.UserAuth;
import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.prueba_tec.DTO.Auth.LoginDTO;
import com.modesto.prueba_tec.DTO.Auth.UsuarioLoggedDTO;
import com.modesto.prueba_tec.DTO.Mapping.UsuarioMap;
import com.modesto.prueba_tec.DTO.Response.RestResponse;
import com.modesto.prueba_tec.Entity.Usuario;
import com.modesto.prueba_tec.Repositories.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMap usuarioMap;
    private final UserAuth userAuth;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, UsuarioMap usuarioMap, UserAuth userAuth, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMap = usuarioMap;
        this.userAuth = userAuth;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Object> iniciarSesion(LoginDTO credenciales) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(credenciales.getEmail());
        if(!usuario.isPresent()  || usuario.get().getEstado() != EstadosDisponibles.ACTIVO) {
            return RestResponse.generateResponse("Credenciales Incorrectas.",
                    HttpStatus.UNAUTHORIZED,null);
        }

        if (passwordEncoder.matches(credenciales.getPassword(), usuario.get().getPassword())) {
            UsuarioLoggedDTO logged =  usuarioMap.toUsuarioLoggedDto(usuario.get());
            logged.setToken(userAuth.generarJwtToken(logged));
            return RestResponse.generateResponse("Sesión iniciada correctamente.",
                    HttpStatus.OK,logged);
        }
        return RestResponse.generateResponse("Credenciales Incorrectas.",
                HttpStatus.UNAUTHORIZED,null);
    }
}
