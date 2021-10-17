package br.com.juliancambraia.cliente.service;

import br.com.juliancambraia.cliente.entities.Cliente;
import br.com.juliancambraia.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    public Cliente findById(Long id) throws Exception {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        return optionalCliente.orElseThrow(() -> new Exception("Não existe cliente com o id: " + id));
    }

    public Cliente findByEmail(String email) throws Exception {
        Optional<Cliente> optionalCliente = clienteRepository.findByEmail(email);
        return optionalCliente.orElseThrow(() -> new Exception("Não existe cliente com o email: " + email));
    }
}
