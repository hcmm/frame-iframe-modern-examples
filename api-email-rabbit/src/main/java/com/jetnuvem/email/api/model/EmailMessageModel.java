package com.jetnuvem.email.api.model;

import java.time.Instant;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "emails")
@Setter
@Getter
public class EmailMessageModel extends RepresentationModel<EmailMessageModel> {
	@Schema(example = "fcea3939-2dbf-441f-a6e7-f550e26c54e9")
	private UUID id;
	
	@Schema(example = "sentoto@gmail.com")
	private String toAddress;
	
	@Schema(example = "fromto@gmail.com")
	private String subject;
	
	@Schema(example = "Seu pedido foi realizado com sucesso.")
    private String text;
	
	@Schema(example = "2024-02-20T12:04:35-03:00")
    private Instant dataEnvio;
	
    
}
