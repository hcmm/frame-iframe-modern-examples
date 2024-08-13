package com.jetnuvem.pedido.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class ClienteModel {
	
	@Schema(example = "fcea3939-2dbf-441f-a6e7-f550e26c54e9")
	private Long id;
	
	@Schema(example = "Henrique Canto")
	private String nome;
	
	@Schema(example = "email@email.com")
    private String email;
	
	@Schema(example = "Rua Araro 430")
    private String endereco;

}
