package com.example.phonebook.services.exceptions;

public class UserWithContactsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UserWithContactsException() {
		super("You can't delete a user with contacts in agenda!");
	}
}
