package com.example.phonebook.services.exceptions;

public class ContactNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ContactNotFoundException(Long id) {
		super("Contact with id " + id + " not found!");
	}
}
