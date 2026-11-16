package com.kauedev.sistema_clientes.dto;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone
) {
}
