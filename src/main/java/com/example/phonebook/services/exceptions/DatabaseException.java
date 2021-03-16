package com.example.phonebook.services.exceptions;

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DatabaseException() {
		super("You can't delete a user with an agenda.");
	}
}
