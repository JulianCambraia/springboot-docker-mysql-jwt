package br.com.juliancambraia.cliente.security.service;

import br.com.juliancambraia.cliente.security.entities.Usuario;
import br.com.juliancambraia.cliente.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }
}
