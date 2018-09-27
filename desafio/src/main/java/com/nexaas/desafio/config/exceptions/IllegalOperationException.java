package com.nexaas.desafio.config.exceptions;

public class IllegalOperationException extends Exception {

	private static final long serialVersionUID = 1938430248035930917L;

	public IllegalOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalOperationException(String message) {
		super(message);
	}

}
