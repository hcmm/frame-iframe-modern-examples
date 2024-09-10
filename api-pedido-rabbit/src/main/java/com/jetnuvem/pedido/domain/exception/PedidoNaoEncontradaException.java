package com.jetnuvem.pedido.domain.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public PedidoNaoEncontradaException(Long id) {
		this(String.format("Não existe um pedido com código %d", id));
	}
	
}
