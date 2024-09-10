package com.jetnuvem.pedido.api.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoInput {

	@NotBlank
	private String descricao;
	@NotBlank
    private double valor;

    @Valid
    private ClienteInput cliente;
	
	
	
}
