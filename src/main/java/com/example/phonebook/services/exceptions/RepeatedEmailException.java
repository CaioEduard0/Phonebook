package com.example.phonebook.services.exceptions;

public class RepeatedEmailException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RepeatedEmailException(String email) {
		super("The email " + email + " was already registered!");
	}
}
