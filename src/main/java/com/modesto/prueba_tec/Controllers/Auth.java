package com.modesto.prueba_tec.Controllers;

import com.modesto.prueba_tec.DTO.Auth.LoginDTO;
import com.modesto.prueba_tec.DTO.Auth.UsuarioLoggedDTO;
import com.modesto.prueba_tec.Services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class Auth {
    private final AuthService authService;

    public Auth(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO credenciales) {
        return authService.iniciarSesion(credenciales);
    }


}
