package com.example.phonebook.services.exceptions;

public class ContactNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ContactNotFoundException(Object idc) {
		super("Contact with id " + idc + " not found.");
	}
}
