package com.kauedev.sistema_clientes.exception;

import java.time.Instant;

public record ApiError(
        Instant timestamp,
        Integer status,
        String error,
        String mensagem,
        String path
) {}
