package com.example.phonebook.services.exceptions;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(Object id) {
		super("User with id " + id + " not found.");
	}
}
