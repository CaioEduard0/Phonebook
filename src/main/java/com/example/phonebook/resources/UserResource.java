package com.example.phonebook.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.dto.UserDTO;
import com.example.phonebook.entities.Contact;
import com.example.phonebook.entities.User;
import com.example.phonebook.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAllUsers() {
		List<User> users = service.findAllUsers();
		List<UserDTO> usersDto = users.stream().map(u -> new UserDTO(u.getName(), u.getEmail())).collect(Collectors.toList());
		return ResponseEntity.ok().body(usersDto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findUser(@PathVariable Long id) {
		User user = service.findUser(id);
		return ResponseEntity.ok().body(new UserDTO(user.getName(), user.getEmail()));
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insertUser(@RequestBody User user) {
		user = service.insertUser(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserDTO(user.getName(), user.getEmail()));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id ) {
		service.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User user) {
		user = service.updateUser(user, id);
		return ResponseEntity.ok().body(new UserDTO(user.getName(), user.getEmail()));
	}
	
	@GetMapping(value = "/{id}/contacts")
	public ResponseEntity<List<ContactDTO>> findContacts(@PathVariable Long id) {
		User user = service.findUser(id);
		List<Contact> contacts = user.getAgenda();
		List<ContactDTO> contactsDto = contacts.stream().map(c -> new ContactDTO(c.getName(), c.getPhone())).collect(Collectors.toList());
		return ResponseEntity.ok().body(contactsDto);
	}
	
	@GetMapping(value = "/{id}/contacts/{idc}")
	public ResponseEntity<ContactDTO> findContact(@PathVariable Long id, @PathVariable Long idc) {
		Contact contact = service.findContact(id, idc);
		return ResponseEntity.ok().body(new ContactDTO(contact.getName(), contact.getPhone()));
	}
	
	@PostMapping(value = "/{id}/contacts")
	public ResponseEntity<ContactDTO> insertContact(@PathVariable Long id, @RequestBody ContactDTO contactDto) {
		Contact contact = service.insertContact(id, contactDto); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idc}").buildAndExpand(contact.getId()).toUri();
		return ResponseEntity.created(uri).body(new ContactDTO(contact.getName(), contact.getPhone()));
	}
	
	@DeleteMapping(value = "/{id}/contacts/{idc}")
	public ResponseEntity<Void> deleteContact(@PathVariable Long id, @PathVariable Long idc) {
		service.deleteContact(id, idc);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}/contacts/{idc}")
	public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @PathVariable Long idc, @RequestBody ContactDTO contactDto) {
		Contact contact = service.updateContact(id, idc, contactDto);
		return ResponseEntity.ok().body(new ContactDTO(contact.getName(), contact.getPhone()));
	}
}
