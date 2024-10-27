package com.modesto.config;

import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;
import com.modesto.prueba_tec.Entity.Usuario;
import com.modesto.prueba_tec.Repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserStarter implements CommandLineRunner {

    private final UsuarioRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserStarter(UsuarioRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String...args) throws Exception {
        Usuario administrador = new Usuario();
        administrador.setNombre("Administrador");
        administrador.setApellido("Administrador");
        administrador.setEmail("administrador@pruebatec.com");
        administrador.setPassword(bCryptPasswordEncoder.encode("administrador"));
        administrador.setRol(RolesDisponibles.ADMIN);
        administrador.setEstado(EstadosDisponibles.ACTIVO);
        userRepository.save(administrador);
    }
}