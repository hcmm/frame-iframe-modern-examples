package com.porto.ecor.mensageria.dto;

import java.util.List;

import lombok.Data;

@Data
public class ClienteDTO {
	private Long id;

    private String nome;
    private String email;
    private String endereco;
    private List<PedidoDTO> pedidos;
    
    
}
