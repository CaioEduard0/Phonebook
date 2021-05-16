package com.example.phonebook.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.example.phonebook.dto.UserCreatorDTO;
import com.example.phonebook.dto.UserDTO;
import com.example.phonebook.entities.User;
import com.example.phonebook.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
		User user = userService.findUserById(id);
		return ResponseEntity.ok(user.userToDto(user));
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<UserDTO> findUserByEmail(@RequestParam String email) {
		User user = userService.findUserByEmail(email);
		return ResponseEntity.ok(user.userToDto(user));
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> insertUser(@Valid @RequestBody UserCreatorDTO userDto) {
		User user = userService.insertUser(userDto.DtoToUser(userDto));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/"+user.getId()).build().toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDto) {
		userService.updateUser(id, userDto.DtoToUser(userDto));
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
