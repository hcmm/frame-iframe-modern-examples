package com.porto.ecor.mensageria.exception;

public class ExponentialBackoffServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ExponentialBackoffServiceException(String mensagem) {
		super(mensagem);
	}

}
