package com.kauedev.sistema_clientes.service;

import com.kauedev.sistema_clientes.model.Cliente;
import com.kauedev.sistema_clientes.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    public Cliente salvar (Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Ja existe um cliente cadastrado com esse Email");
        }
        return clienteRepository.save(cliente);
    }
         public List<Cliente> listartodos() {
             return clienteRepository.findAll();
    }
        public Optional<Cliente> buscarPorId(Long id) {
            return clienteRepository.findById(id);
        }
        public Cliente atualizar(Long id, Cliente dadosNovos) {
            Cliente clienteexistente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));


            if (!clienteexistente.getEmail().equals(dadosNovos.getEmail()) && clienteRepository.existsByEmail(dadosNovos.getEmail())) {
                throw new IllegalArgumentException("O novo Email já está em uso por outro cliente");

            }
            clienteexistente.setNome(dadosNovos.getNome());
            clienteexistente.setEmail(dadosNovos.getEmail());
            clienteexistente.setTelefone(dadosNovos.getTelefone());

            return clienteRepository.save(clienteexistente);
        }
        public void deletar (Long id){
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
        }
}
