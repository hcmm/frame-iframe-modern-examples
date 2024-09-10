package com.jetnuvem.pedido.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jetnuvem.pedido.domain.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	Pedido findByDescricao(String descricao);

	@Query("SELECT p FROM Pedido p WHERE p.id = ?1")
    Optional<Pedido>  buscaPorId(Long id);

}
