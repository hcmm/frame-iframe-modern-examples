package com.jetnuvem.email.domain.exception;

import java.util.UUID;

public class EmailNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EmailNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public EmailNaoEncontradaException(UUID uuid) {
		this(String.format("Não existe uma email com código %d", uuid));
	}
	
}
