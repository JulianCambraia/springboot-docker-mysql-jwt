package br.com.juliancambraia.cliente.resource;

import br.com.juliancambraia.cliente.entities.Cliente;
import br.com.juliancambraia.cliente.request.ClienteRequest;
import br.com.juliancambraia.cliente.response.ClienteResponse;
import br.com.juliancambraia.cliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "cliente")
@RequiredArgsConstructor
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> save(@RequestBody ClienteRequest request) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(request, cliente);
        clienteService.save(cliente);

        ClienteResponse response = new ClienteResponse();
        BeanUtils.copyProperties(cliente, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> findAll() {
        List<Cliente> listaCliente = clienteService.findAll();

        List<ClienteResponse> clienteResponseList = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        listaCliente.forEach(cliente -> clienteResponseList.add(mapper.map(cliente, ClienteResponse.class)));

        return ResponseEntity.ok(clienteResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> findById(@PathVariable(name = "id") Long id) {
        Cliente cliente = null;

        try {
            cliente = clienteService.findById(id);

            ClienteResponse clienteResponse = new ClienteResponse();
            BeanUtils.copyProperties(cliente, clienteResponse);

            return ResponseEntity.ok().body(clienteResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ClienteResponse.builder().error(e.getMessage()).build());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponse> findByEmail(@PathVariable(name = "email") String email) {

        try {
            Cliente cliente = clienteService.findByEmail(email);
            ClienteResponse clienteResponse = new ClienteResponse();
            BeanUtils.copyProperties(cliente, clienteResponse);

            return ResponseEntity.ok().body(clienteResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ClienteResponse.builder().error(e.getMessage()).build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> update(@PathVariable(name = "id") Long id, @RequestBody ClienteRequest request) {
        try {
            Cliente cliente = clienteService.findById(id);
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setSkipNullEnabled(true);
            mapper.map(request, cliente);
            cliente.setId(id);
            clienteService.save(cliente);

            ClienteResponse clienteResponse = new ClienteResponse();
            BeanUtils.copyProperties(cliente, clienteResponse);

            return ResponseEntity.status(HttpStatus.OK).body(clienteResponse);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ClienteResponse.builder().error(e.getMessage()).build());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
        try {
            clienteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(ClienteResponse.builder().error("Cliente não existe ou inválido: " + id).build());
        }
    }


}
