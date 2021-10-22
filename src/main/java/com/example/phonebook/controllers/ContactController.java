package com.example.phonebook.controllers;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.dto.ContactResponseDTO;
import com.example.phonebook.services.ContactService;
import lombok.AllArgsConstructor;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users", consumes = "application/json", produces = "application/json")
@AllArgsConstructor
public class ContactController {
	
	private final ContactService contactService;
	
	@GetMapping(value = "/{userId}/contacts")
	public ResponseEntity<Page<ContactResponseDTO>> findAll(@PathVariable Long userId, @RequestParam Integer page, @RequestParam Integer size) {
		List<ContactResponseDTO> contacts = contactService.findAll(userId);
		return ResponseEntity.ok(new PageImpl<>(contacts, PageRequest.of(page, size), contacts.size()));
	}
	
	@GetMapping(value = "/{userId}/contacts")
	public ResponseEntity<Page<ContactResponseDTO>> find(@PathVariable Long userId, @RequestParam String name, @RequestParam Integer page, @RequestParam Integer size) {
		List<ContactResponseDTO> contacts = contactService.find(userId, name);
		return ResponseEntity.ok(new PageImpl<>(contacts, PageRequest.of(page, size), contacts.size()));
	}
	
	@PostMapping(value = "/contacts")
	public ResponseEntity<ContactResponseDTO> create(@Valid @RequestBody ContactDTO contactDto) {
		ContactResponseDTO contact = contactService.create(contactDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/"+contact.getId()).build().toUri();
		return ResponseEntity.created(uri).body(contact);
	}
	
	@PutMapping(value = "/{userId}/contacts/{contactId}")
	public ResponseEntity<ContactResponseDTO> update(@PathVariable Long userId, @PathVariable Long contactId, @Valid @RequestBody ContactDTO contactDto) {
		return ResponseEntity.ok(contactService.update(userId, contactId, contactDto));
	}
	
	@DeleteMapping(value = "/{userId}/contacts/{contactId}")
	public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long contactId) {
		contactService.delete(userId, contactId);
		return ResponseEntity.noContent().build();
	}
}
