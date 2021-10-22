package com.example.phonebook.controllers;

import com.example.phonebook.dto.UserDTO;
import com.example.phonebook.dto.UserResponseDTO;
import com.example.phonebook.dto.UserUpdateDTO;
import com.example.phonebook.services.UserService;
import lombok.AllArgsConstructor;
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

@RestController
@RequestMapping(value = "/users", consumes = "application/json", produces = "application/json")
@AllArgsConstructor
public class UserController {
	
	private UserService userService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserResponseDTO> find(@PathVariable Long id) {
		return ResponseEntity.ok(userService.find(id));
	}
	
	@GetMapping
	public ResponseEntity<UserResponseDTO> find(@RequestParam String email) {
		return ResponseEntity.ok(userService.find(email));
	}
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserDTO userDto) {
		UserResponseDTO user = userService.create(userDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/"+user.getId()).build().toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping
	public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UserUpdateDTO userDto) {
		return ResponseEntity.ok(userService.update(userDto));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
