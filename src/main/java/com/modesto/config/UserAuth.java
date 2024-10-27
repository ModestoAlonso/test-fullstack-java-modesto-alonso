package com.modesto.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.prueba_tec.DTO.Auth.UsuarioLoggedDTO;
import com.modesto.prueba_tec.Entity.Usuario;
import com.modesto.prueba_tec.Repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;


@Component
public class UserAuth {
    private String  secretKey = "0d800e93cb8b3de67cc703fd9921a18ff6216172c4f2e1e79e9c3ef1615b0e79";
    private  final UsuarioRepository usuarioRepository;

    public UserAuth(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generarJwtToken(UsuarioLoggedDTO loggedUser) {

        return JWT.create()
                .withSubject(loggedUser.getEmail())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(5400))
                .withClaim("rol", loggedUser.getRol().getAuthority())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validarJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        try{
            DecodedJWT decoded = verifier.verify(token);

            Usuario usuario = usuarioRepository.findByEstadoAndEmailIgnoreCase(EstadosDisponibles.ACTIVO,decoded.getSubject());

            return new UsernamePasswordAuthenticationToken(usuario, null, Collections.singletonList(usuario.getRol()));

        } catch (Exception e) {
            return null;
        }


    }

}
