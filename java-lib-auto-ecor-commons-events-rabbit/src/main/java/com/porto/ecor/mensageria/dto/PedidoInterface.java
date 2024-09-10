package com.porto.ecor.mensageria.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface PedidoInterface {
	Long getId();

	void setId(Long id);

	String getDescricao();

	void setDescricao(String descricao);

	double getValor();

	void setValor(double valor);

	ClienteDTO getCliente();

	void setCliente(ClienteDTO cliente);
}
