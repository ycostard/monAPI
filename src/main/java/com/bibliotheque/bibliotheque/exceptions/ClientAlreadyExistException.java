package com.bibliotheque.bibliotheque.exceptions;

public class ClientAlreadyExistException extends RuntimeException{
	
	public ClientAlreadyExistException(String message) {
		super(message);
	}
}