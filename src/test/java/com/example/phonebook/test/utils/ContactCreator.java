package com.example.phonebook.test.utils;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.entities.Contact;

public class ContactCreator {
	
	public static Contact createContact() {
		Contact contact = new Contact();
		contact.setName("Mario");
		contact.setPhone("9999-9999");
		return contact;
	}
	
	public static Contact createValidContact() {
		Contact contact = createContact();
		contact.setId(1L);
		return contact;
	}
	
	public static ContactDTO createContactToBeSaved() {
		ContactDTO contact = new ContactDTO();
		contact.setName("Mario");
		contact.setPhone("9999-9999");
		return contact;
	}
	
	public static Contact createContactToUpdate() {
		Contact contact = new Contact();
		contact.setName("Pedro");
		contact.setPhone("5555-5555");
		return contact;
	}
	
	public static Contact createContactWithUser() {
		Contact contact = createContact();
		contact.setId(1L);
		contact.setUser(UserCreator.createUserToBeDeleted());
		return contact;
	}
	
	public static ContactDTO createContactToError() {
		ContactDTO contact = new ContactDTO();
		contact.setName("M");
		contact.setPhone("9");
		return contact;
	}
}
