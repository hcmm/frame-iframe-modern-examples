package com.jetnuvem.email.api.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailMessageInput {

	@Schema(example = "sentoto@gmail.com")
	@NotBlank
	private String toAddress;
	
	@Schema(example = "fromto@gmail.com")
	@NotBlank
	private String subject;
	
	@Schema(example = "Seu pedido foi realizado com sucesso.")
	@NotBlank
    private String text;
	
	
	
	
	
	
}
