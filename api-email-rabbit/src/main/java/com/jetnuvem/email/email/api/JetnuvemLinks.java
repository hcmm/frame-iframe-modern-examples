package com.jetnuvem.email.email.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.stereotype.Component;

import com.jetnuvem.email.api.controller.EmailMessageController;

@Component
public class JetnuvemLinks {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projecao", VariableType.REQUEST_PARAM));
	
	
	
	public Link linkToAplicacoes(String rel) {
		return linkTo(EmailMessageController.class).withRel(rel);
	}
	
	public Link linkToAplicacoes() {
		return linkToAplicacoes(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToAplicacoes(UUID emaiId, String rel) {
		return linkTo(methodOn(EmailMessageController.class)
				.buscar(emaiId)).withRel(rel);
	}
	
	public Link linkToAplicacoes(UUID emaiId) {
		return linkToAplicacoes(emaiId, IanaLinkRelations.SELF.value());
	}
	
	
	public Link linkToParametros() {
		return linkToAplicacoes(IanaLinkRelations.SELF.value());
	}
	
}
