package com.example.phonebook.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.entities.Contact;
import com.example.phonebook.services.ContactService;

@RestController
@RequestMapping(value = "/users")
public class ContactController {
	
	private ContactService contactService;
	
	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}
	
	@GetMapping(value = "/{id}/contacts")
	public ResponseEntity<Page<ContactDTO>> findAllUserContacts(@PathVariable Long id) {
		List<Contact> contacts = contactService.findAllContacts(id);
		List<ContactDTO> contactsDto = contacts.stream().map(con -> con.contactToDto(con)).collect(Collectors.toList());		
		return ResponseEntity.ok(new PageImpl<>(contactsDto, PageRequest.of(1, 5), contactsDto.size()));
	}
	
	@GetMapping(value = "/{id}/contacts/find")
	public ResponseEntity<Page<ContactDTO>> findContactsByName(@PathVariable Long id, @RequestParam String name) {
		List<Contact> contacts = contactService.findContactsByName(id, name);
		List<ContactDTO> contactsDto = contacts.stream().map(con -> con.contactToDto(con)).collect(Collectors.toList());
		return ResponseEntity.ok(new PageImpl<>(contactsDto, PageRequest.of(1, 5), contactsDto.size()));
	}
	
	@PostMapping(value = "/{id}/contacts")
	public ResponseEntity<Void> insertContact(@PathVariable Long id, @Valid @RequestBody ContactDTO contactDto) {
		Contact contact = contactService.insertContact(id, contactDto); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/"+contact.getId()).build().toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{userId}/contacts/{contactId}")
	public ResponseEntity<Void> updateContact(@PathVariable Long userId, @PathVariable Long contactId, @Valid @RequestBody ContactDTO contactDto) {
		contactService.updateContact(userId, contactId, contactDto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value = "/{userId}/contacts/{contactId}")
	public ResponseEntity<Void> deleteContact(@PathVariable Long userId, @PathVariable Long contactId) {
		contactService.deleteContact(userId, contactId);
		return ResponseEntity.noContent().build();
	}
}
