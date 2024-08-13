package com.jetnuvem.pedido.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.stereotype.Component;

import com.jetnuvem.pedido.api.controller.PedidoController;

@Component
public class JetnuvemLinks {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projecao", VariableType.REQUEST_PARAM));
	
	
	
	public Link linkToAplicacoes(String rel) {
		return linkTo(PedidoController.class).withRel(rel);
	}
	
	public Link linkToAplicacoes() {
		return linkToAplicacoes(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToAplicacoes(Long pedidoId, String rel) {
		return linkTo(methodOn(PedidoController.class)
				.buscar(pedidoId)).withRel(rel);
	}
	
	public Link linkToAplicacoes(Long pedidoId) {
		return linkToAplicacoes(pedidoId, IanaLinkRelations.SELF.value());
	}
	
	
	public Link linkToParametros() {
		return linkToAplicacoes(IanaLinkRelations.SELF.value());
	}
	
}
