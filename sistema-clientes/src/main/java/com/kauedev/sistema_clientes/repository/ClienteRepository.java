package com.kauedev.sistema_clientes.repository;

import com.kauedev.sistema_clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByEmail(String email);


    boolean existsByTelefone(String telefone);

    Optional<Cliente> findByEmail(String email);
}
