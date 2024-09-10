package com.jetnuvem.pedido.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.jetnuvem.pedido.api.controller.PedidoController;
import com.jetnuvem.pedido.api.model.PedidoModel;
import com.jetnuvem.pedido.domain.model.Pedido;

@Component
public class PedidoModelAssembler 
		extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	
	
	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}
	
	@Override
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getId(), pedido);
		modelMapper.map(pedido, pedidoModel);
		
		
		return pedidoModel;
	}
	
	
	public Page<PedidoModel> toModel(Page<Pedido> pedidosPage) {
        return pedidosPage.map(pedido -> modelMapper.map(pedido, PedidoModel.class));

	}
	
}
