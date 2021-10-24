package br.com.juliancambraia.cliente.security.resource;

import br.com.juliancambraia.cliente.security.entities.Usuario;
import br.com.juliancambraia.cliente.security.request.UserRequest;
import br.com.juliancambraia.cliente.security.request.UsuarioJwtRequest;
import br.com.juliancambraia.cliente.security.response.UserResponse;
import br.com.juliancambraia.cliente.security.response.UsuarioJwtResponse;
import br.com.juliancambraia.cliente.security.service.UsuarioService;
import br.com.juliancambraia.cliente.security.service.impl.UserDetailsServiceImpl;
import br.com.juliancambraia.cliente.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioService usuarioService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> save(@RequestBody @Valid UserRequest userRequest) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(userRequest, usuario);
        usuario.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        usuarioService.save(usuario);
        log.info("Usuário cadastrado com sucesso: {} ", usuario);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(usuario, userResponse);

        return ResponseEntity.ok().body(userResponse);
    }

    @PostMapping("/autenticacao")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioJwtResponse getAutenticacao(@RequestBody UsuarioJwtRequest usuarioJwtRequest) throws Exception {
        try {
            UserDetails userDetails = userDetailsServiceImpl.autenticar(usuarioJwtRequest);
            String token = jwtUtil.obterToken(usuarioJwtRequest);
            return UsuarioJwtResponse.builder()
                    .email(userDetails.getUsername())
                    .token(token)
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
