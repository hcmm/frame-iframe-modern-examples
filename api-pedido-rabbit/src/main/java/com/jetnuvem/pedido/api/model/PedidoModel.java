package com.jetnuvem.pedido.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@Setter
@Getter
public class PedidoModel extends RepresentationModel<PedidoModel> {
	
	@Schema(example = "fcea3939-2dbf-441f-a6e7-f550e26c54e9")
	private Long id;
	
	@Schema(example = "Pedido Arquiteura")
	private String descricao;
	
	@Schema(example = "250.00")
    private double valor;

    private ClienteModel cliente;
	
    
}
