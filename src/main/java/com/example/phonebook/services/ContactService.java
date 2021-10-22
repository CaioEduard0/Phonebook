package com.example.phonebook.services;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.dto.ContactResponseDTO;
import com.example.phonebook.entities.Contact;
import com.example.phonebook.entities.User;
import com.example.phonebook.exceptions.NotFoundException;
import com.example.phonebook.mappers.ContactMapper;
import com.example.phonebook.repositories.ContactRepository;
import com.example.phonebook.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ContactService {

	private final UserRepository userRepository;
	private final ContactRepository contactRepository;
	private final ContactMapper contactMapper;
	
	public List<ContactResponseDTO> findAll(Long userId) {
		User user = find(userId);
		return user.getAgenda().stream().map(contactMapper::toDTO).collect(Collectors.toList());
	}
	
	public List<ContactResponseDTO> find(Long userId, String name) {
		User user = find(userId);
		List<Contact> contacts = contactRepository.findByName(name);
		return contacts.stream().filter(contact -> contact.getUser() == user).map(contactMapper::toDTO).collect(Collectors.toList());
	}
	
	public ContactResponseDTO create(ContactDTO contactDto) {
		find(contactDto.getUser().getId());
		Contact contact = contactRepository.save(contactMapper.toEntity(contactDto));
		return contactMapper.toDTO(contact);
	}
	
	public ContactResponseDTO update(Long userId, Long contactId, ContactDTO contactDto) {
		find(userId, contactId);
		Contact contact = contactRepository.save(contactMapper.toEntity(contactDto));
		return contactMapper.toDTO(contact);
	}
	
	public void delete(Long userId, Long contactId) {
		find(userId, contactId);
		contactRepository.deleteById(contactId);
	}
	
	private User find(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(NotFoundException::new);
	}

	private void find(Long userId, Long contactId) {
		User user = find(userId);
		Optional<Contact> contact = user.getAgenda().stream().filter(possibleContact -> Objects.equals(possibleContact.getId(), contactId)).findFirst();
		contact.orElseThrow(NotFoundException::new);
	}
}
