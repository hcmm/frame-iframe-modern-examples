package com.jetnuvem.pedido.api.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteInput {
	
	@NotBlank
	private String nome;
	@NotBlank
    private String email;
	@NotBlank
    private String endereco;
}
