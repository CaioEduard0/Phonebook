package com.example.phonebook.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.phonebook.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.entities.Contact;
import com.example.phonebook.entities.User;
import com.example.phonebook.repositories.ContactRepository;
import com.example.phonebook.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactService {

	private final UserRepository userRepository;
	private final ContactRepository contactRepository;
	
	public ContactService(UserRepository userRepository, ContactRepository contactRepository) {
		this.userRepository = userRepository;
		this.contactRepository = contactRepository;
	}
	
	public List<Contact> findAllContacts(Long id) {
		User user = findUser(id);
		return user.getAgenda();
	}
	
	public List<Contact> findContactsByName(Long id, String name) {
		User user = findUser(id);
		List<Contact> contacts = contactRepository.findByName(name);
		return contacts.stream().filter(con -> con.getUser() == user).collect(Collectors.toList());
	}
	
	public Contact insertContact(Long id, ContactDTO contactDto) {
		User user = findUser(id);
		Contact contact = new Contact();
		contact.setName(contactDto.getName());
		contact.setPhone(contactDto.getPhone());
		contact.setUser(user);
		return contactRepository.save(contact);
	}
	
	public void updateContact(Long userId, Long contactId, ContactDTO contactDto) {
		Contact contact = findContact(userId, contactId);
		contact.setName(contactDto.getName());
		contact.setPhone(contactDto.getPhone());
		contactRepository.save(contact);	
	}
	
	public void deleteContact(Long userId, Long contactId) {
		Contact contact = findContact(userId, contactId);
		contactRepository.delete(contact);
	}
	
	private User findUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(NotFoundException::new);
	}
		
	private Contact findContact(Long userId, Long contactId) {
		User user = findUser(userId);
		List<Contact> contacts = user.getAgenda();
		Contact contact = null;
		for (Contact con : contacts) {
			if (Objects.equals(con.getId(), contactId)) {
				contact = con;
			}
		}
		if (contact == null) {
			throw new NotFoundException();
		}
		return contact;	
	}
}
