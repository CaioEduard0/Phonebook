package com.example.phonebook.services.exceptions;

public class RepeatedUsernameException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RepeatedUsernameException(String username) {
		super("The username " + username + " was already registered!");
	}
}
