package com.kauedev.sistema_clientes.service;

import com.kauedev.sistema_clientes.dto.ClienteRequestDTO;
import com.kauedev.sistema_clientes.dto.ClienteResponseDTO;
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

    public ClienteResponseDTO salvar(ClienteRequestDTO dto) {
        if (clienteRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Ja existe um cliente cadastrado com esse Email");
        }
        if (dto.telefone() != null && clienteRepository.existsByTelefone(dto.telefone())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse telefone");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());

        Cliente clienteSalvo = clienteRepository.save(cliente);
        return toResponseDTO(clienteSalvo);
    }

    public List<ClienteResponseDTO> listartodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Optional<ClienteResponseDTO> buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(this::toResponseDTO);
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dadosNovos) {
        Cliente clienteexistente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        if (!clienteexistente.getEmail().equals(dadosNovos.email()) && clienteRepository.existsByEmail(dadosNovos.email())) {
            throw new IllegalArgumentException("O novo Email já está em uso por outro cliente");
        }

        clienteexistente.setNome(dadosNovos.nome());
        clienteexistente.setEmail(dadosNovos.email());
        clienteexistente.setTelefone(dadosNovos.telefone());

        Cliente clienteAtualizado = clienteRepository.save(clienteexistente);
        return toResponseDTO(clienteAtualizado);
    }

    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }

    public ClienteResponseDTO atualizarPatch(Long id, ClienteRequestDTO dadosParciais) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + id));

        if (dadosParciais.nome() != null && !dadosParciais.nome().isBlank()) {
            clienteExistente.setNome(dadosParciais.nome());
        }
        if (dadosParciais.email() != null && !dadosParciais.email().isBlank()) {
            if (!clienteExistente.getEmail().equals(dadosParciais.email()) && clienteRepository.existsByEmail(dadosParciais.email())) {
                throw new IllegalArgumentException("Email já em uso!!");
            }
            clienteExistente.setEmail(dadosParciais.email());
        }
        if (dadosParciais.telefone() != null && !dadosParciais.telefone().isBlank()) {
            boolean telefoneJaExiste = clienteRepository.existsByTelefone(dadosParciais.telefone());
            boolean telefoneDiferentedoAtual = !dadosParciais.telefone().equals(clienteExistente.getTelefone());
            if (telefoneDiferentedoAtual && telefoneJaExiste) {
                throw new IllegalArgumentException("Telefone em Uso");
            }
            clienteExistente.setTelefone(dadosParciais.telefone());
        }

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        return toResponseDTO(clienteAtualizado);
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone()
        );
    }
}