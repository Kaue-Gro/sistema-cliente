package com.kauedev.sistema_clientes.controller;


import com.kauedev.sistema_clientes.dto.ClienteRequestDTO;
import com.kauedev.sistema_clientes.dto.ClienteResponseDTO;
import com.kauedev.sistema_clientes.model.Cliente;
import com.kauedev.sistema_clientes.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvar (@Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO clientesalvo = clienteService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientesalvo);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos () {
        return ResponseEntity.ok(clienteService.listartodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId (@PathVariable Long id){
        return clienteService.buscarPorId(id)
                .map(cliente -> ResponseEntity.ok(cliente))
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar (@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dadosnovos) {
        ClienteResponseDTO clienteAtualizado = clienteService.atualizar(id, dadosnovos);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarparcial (@PathVariable Long id, @RequestBody ClienteRequestDTO dadosParciais) {
        ClienteResponseDTO clienteAtualizado = clienteService.atualizarPatch(id, dadosParciais);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
