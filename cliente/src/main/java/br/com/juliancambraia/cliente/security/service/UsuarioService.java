package br.com.juliancambraia.cliente.security.service;

import br.com.juliancambraia.cliente.security.entities.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Optional<Usuario> findByEmail(String email);

    Usuario save(Usuario usuario);

}
