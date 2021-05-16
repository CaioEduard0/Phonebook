package com.example.phonebook.services.exceptions;

public class EmailNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmailNotFoundException(String email) {
		super("User with email " + email + " not found!");
	}

}
