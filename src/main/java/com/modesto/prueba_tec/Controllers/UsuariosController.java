package com.modesto.prueba_tec.Controllers;

import com.modesto.config.CaseInsensitiveEnumEditor;
import com.modesto.constants.auth.EstadosDisponibles;
import com.modesto.constants.auth.RolesDisponibles;
import com.modesto.prueba_tec.DTO.Response.RestResponse;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioDTO;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioModificar;
import com.modesto.prueba_tec.DTO.Usuarios.UsuarioRegistrar;
import com.modesto.prueba_tec.Entity.Usuario;
import com.modesto.prueba_tec.Filters.UsuarioFilter;
import com.modesto.prueba_tec.Services.UsuarioService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UsuariosController {
    private final UsuarioService usuarioService;

    public UsuariosController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(RolesDisponibles.class, new CaseInsensitiveEnumEditor(RolesDisponibles.class));
        binder.registerCustomEditor(EstadosDisponibles.class, new CaseInsensitiveEnumEditor(EstadosDisponibles.class));
    }

    /**
     * Endpoint para obtener usuarios.
     * @param nombre Filtro nombre
     * @param apellido Filtro apellido
     * @param email Filtro email
     * @param estado  estado
     * @param rol Filtro rol
     * @param paginaNumero Filtro paginaNumero
     * @param size Filtro size
     * @return
     */
    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('CONSULTOR', 'ADMIN')")
    ResponseEntity<Object> listar(@RequestParam(name = "nombre", required = false) String nombre,
                            @RequestParam(name = "apellido", required = false) String apellido,
                            @RequestParam(name = "email", required = false) String email,
                            @RequestParam(name = "estado", required = false) List<EstadosDisponibles> estado,
                            @RequestParam(name = "rol", required = false) List<RolesDisponibles> rol,
                            @RequestParam(name = "pagina", required = false) Integer paginaNumero,
                            @RequestParam(name = "paginaSize", required = false) Integer size) {

            UsuarioFilter filtrosCampos = new UsuarioFilter(nombre,apellido,email,rol,estado);
            Integer pagina = paginaNumero == null || paginaNumero < 0 ? 0 : paginaNumero;
            Integer paginaSize = size == null || size == 0 ? 25 : size;

            return usuarioService.find(filtrosCampos, pagina, paginaSize);

    }

    /**
     * Endpoint para crear usuarios nuevos. Solo ADMIN tiene acceso.
     * @param usuarioRegistrar
     * @return
     */
    @PostMapping("/crear")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<Object> crear(@RequestBody UsuarioRegistrar usuarioRegistrar) {
        return usuarioService.registrar(usuarioRegistrar);
    }

    /**
     * Endpoint para eliminar/desactviar usuarios. Solo ADMIN tiene acceso.
     * @param id Id del usuario a eliminar.
     * @return
     */
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        return usuarioService.eliminar(id);
    }

    /**
     * Endpoint para editar usuarios. Solo ADMIN tiene acceso.
     * @param id Id del usuario a editar
     * @param usuarioModificar Campos a editar.
     * @return
     */
    @PutMapping("/editar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<Object> update(@PathVariable("id") Integer id, @RequestBody UsuarioModificar usuarioModificar) {
        return usuarioService.editar(id,usuarioModificar);
    }
}
