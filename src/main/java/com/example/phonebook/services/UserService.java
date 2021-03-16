package com.example.phonebook.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.entities.Contact;
import com.example.phonebook.entities.User;
import com.example.phonebook.repositories.ContactRepository;
import com.example.phonebook.repositories.UserRepository;
import com.example.phonebook.services.exceptions.ContactNotFoundException;
import com.example.phonebook.services.exceptions.DatabaseException;
import com.example.phonebook.services.exceptions.UserNotFoundException;

import javassist.bytecode.CodeAttribute.RuntimeCopyException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository; 
	
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	public User findUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new UserNotFoundException(id));
	}
	
	public User insertUser(User user) {
		return userRepository.save(user);
	}
	
	public void deleteUser(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException();
		}
	}
	
	public User updateUser(User user, Long id) {
		try {
			User newUser = userRepository.getOne(id);
			updatedUser(newUser, user);
			return userRepository.save(newUser);
		} catch (EntityNotFoundException e) {
			throw new UserNotFoundException(id);
		}
	}
	
	private void updatedUser(User newUser, User user) {
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
	}
	
	public Contact findContact(Long id, Long idc) {
		Contact contact = rightContact(id, idc);
		return contact;
	}
	
	public Contact insertContact(Long id, ContactDTO contactDto) {
		Optional<User> user = userRepository.findById(id);
		Contact contact = new Contact(null,	contactDto.getName(), contactDto.getPhone(), user.get());
		return contactRepository.save(contact);
	}
	
	public void deleteContact(Long id, Long idc) {
		Contact contact = rightContact(id, idc);
		contactRepository.delete(contact);
	}
	
	public Contact updateContact(Long id, Long idc, ContactDTO contactDto) {
		Contact contact = rightContact(id, idc);
		updatedContact(contact, contactDto);
		return contactRepository.save(contact);		
	}
	
	private Contact rightContact(Long id, Long idc) {
		Optional<User> user = userRepository.findById(id);
		user.orElseThrow(() -> new UserNotFoundException(id));
		List<Contact> contacts = user.get().getAgenda();
		Contact contact = null;
		for (Contact x : contacts) {
			if (x.getId() == idc) {
				contact = x;
			}
		}
		if (contact == null) {
			throw new ContactNotFoundException(idc);
		}
		return contact;	
	}
	
	private void updatedContact(Contact contact, ContactDTO contactDto) {
		contact.setName(contactDto.getName());
		contact.setPhone(contactDto.getPhone());
	}
}
